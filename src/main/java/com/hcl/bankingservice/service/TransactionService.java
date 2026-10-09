package com.hcl.bankingservice.service;

import com.hcl.bankingservice.dao.service.AccountDaoService;
import com.hcl.bankingservice.dao.service.CreditCardDaoService;
import com.hcl.bankingservice.dao.service.DebitCardDaoService;
import com.hcl.bankingservice.dao.service.TransactionDaoService;
import com.hcl.bankingservice.dto.TransactionDto;
import com.hcl.bankingservice.enums.TransactionType;
import com.hcl.bankingservice.exception.AccountIdNotFoundException;
import com.hcl.bankingservice.exception.InsufficientBalanceException;
import com.hcl.bankingservice.exception.NoCardUsedException;
import com.hcl.bankingservice.exception.NoCreditCardFoundException;
import com.hcl.bankingservice.exception.NoDebitCardFoundException;
import com.hcl.bankingservice.exception.TransactionIdNotFoundException;
import com.hcl.bankingservice.mapper.TransactionMapper;
import com.hcl.bankingservice.model.Account;
import com.hcl.bankingservice.model.CreditCard;
import com.hcl.bankingservice.model.DebitCard;
import com.hcl.bankingservice.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class.getName());

    private final TransactionDaoService transactionDaoService;
    private final CreditCardDaoService creditCardDaoService;
    private final DebitCardDaoService debitCardDaoService;
    private final AccountDaoService accountDaoService;

    public TransactionService(TransactionDaoService transactionDaoService,
                              CreditCardDaoService creditCardDaoService,
                              DebitCardDaoService debitCardDaoService,
                              AccountDaoService accountDaoService) {
        this.transactionDaoService = transactionDaoService;
        this.creditCardDaoService = creditCardDaoService;
        this.debitCardDaoService = debitCardDaoService;
        this.accountDaoService = accountDaoService;
    }

    public List<TransactionDto> getAll() {
        LOGGER.debug("Fetching all Transactions");
        List<Transaction> transactionList = transactionDaoService.getAll();
        return TransactionMapper.toDto(transactionList);
    }

    public TransactionDto getOneById(long id) {
        LOGGER.debug("Fetching Transaction with id:{}", id);
        Optional<Transaction> optionalTransaction = transactionDaoService.getOneById(id);
        if (optionalTransaction.isEmpty()) {
            LOGGER.error("Transaction Not Found with id:{}", id);
            throw new TransactionIdNotFoundException(id);
        }
        return TransactionMapper.toDto(optionalTransaction.get());
    }

    /**
     * Creates a transaction using either a credit card or a debit card and updates the
     * account balance. Both writes run in ONE database transaction, so if anything fails
     * (for example insufficient balance) nothing is saved.
     */
    @Transactional
    public TransactionDto create(TransactionDto transactionDto) {
        LOGGER.info("Initiating a Transaction");

        if (transactionDto.getCreditCardId() != 0) {
            CreditCard creditCard = creditCardDaoService.getOneById(transactionDto.getCreditCardId())
                    .orElseThrow(() -> {
                        LOGGER.error("Credit card not found with id:{}", transactionDto.getCreditCardId());
                        return new NoCreditCardFoundException();
                    });
            Transaction transaction = TransactionMapper.toEntity(transactionDto);
            transaction.setCreditCard(creditCard);
            transaction.setDebitCard(null);
            creditCard.getTransactions().add(transaction);
            return process(transaction, creditCard.getAccount().getAccountId());
        }

        if (transactionDto.getDebitCardId() != 0) {
            DebitCard debitCard = debitCardDaoService.getOneById(transactionDto.getDebitCardId())
                    .orElseThrow(() -> {
                        LOGGER.error("Debit card not found with id:{}", transactionDto.getDebitCardId());
                        return new NoDebitCardFoundException();
                    });
            Transaction transaction = TransactionMapper.toEntity(transactionDto);
            transaction.setDebitCard(debitCard);
            transaction.setCreditCard(null);
            debitCard.getTransactions().add(transaction);
            return process(transaction, debitCard.getAccount().getAccountId());
        }

        LOGGER.error("No card used for the transaction");
        throw new NoCardUsedException();
    }

    public boolean delete(long id) {
        return transactionDaoService.delete(id);
    }

    private TransactionDto process(Transaction transaction, long accountId) {
        Account account = accountDaoService.getOneById(accountId)
                .orElseThrow(() -> new AccountIdNotFoundException(accountId));

        applyToBalance(account, transaction);

        Transaction savedTransaction = transactionDaoService.create(transaction);
        accountDaoService.update(account);
        LOGGER.info("Transaction completed with id:{}", savedTransaction.getTransactionId());
        return TransactionMapper.toDto(savedTransaction);
    }

    private void applyToBalance(Account account, Transaction transaction) {
        BigDecimal amount = transaction.getTransactionAmount();
        if (transaction.getTransactionType() == TransactionType.DEPOSIT) {
            account.setBalance(account.getBalance().add(amount));
        } else if (transaction.getTransactionType() == TransactionType.WITHDRAW) {
            if (amount.compareTo(account.getBalance()) > 0) {
                LOGGER.error("Balance not sufficient to make transaction");
                throw new InsufficientBalanceException();
            }
            account.setBalance(account.getBalance().subtract(amount));
        }
    }
}
