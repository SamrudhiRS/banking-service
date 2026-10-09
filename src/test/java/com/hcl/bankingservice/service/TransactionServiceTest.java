package com.hcl.bankingservice.service;

import com.hcl.bankingservice.dao.service.AccountDaoService;
import com.hcl.bankingservice.dao.service.CreditCardDaoService;
import com.hcl.bankingservice.dao.service.DebitCardDaoService;
import com.hcl.bankingservice.dao.service.TransactionDaoService;
import com.hcl.bankingservice.dto.TransactionDto;
import com.hcl.bankingservice.enums.TransactionType;
import com.hcl.bankingservice.exception.InsufficientBalanceException;
import com.hcl.bankingservice.exception.NoCardUsedException;
import com.hcl.bankingservice.exception.NoCreditCardFoundException;
import com.hcl.bankingservice.exception.NoDebitCardFoundException;
import com.hcl.bankingservice.exception.TransactionIdNotFoundException;
import com.hcl.bankingservice.model.Account;
import com.hcl.bankingservice.model.CreditCard;
import com.hcl.bankingservice.model.DebitCard;
import com.hcl.bankingservice.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    private static final long ACCOUNT_ID = 1L;
    private static final long CREDIT_CARD_ID = 10L;
    private static final long DEBIT_CARD_ID = 20L;

    @Mock
    private TransactionDaoService transactionDaoService;
    @Mock
    private CreditCardDaoService creditCardDaoService;
    @Mock
    private DebitCardDaoService debitCardDaoService;
    @Mock
    private AccountDaoService accountDaoService;

    @InjectMocks
    private TransactionService transactionService;

    private Account account;
    private CreditCard creditCard;
    private DebitCard debitCard;

    @BeforeEach
    void setUp() {
        account = new Account();
        account.setAccountId(ACCOUNT_ID);
        account.setBalance(new BigDecimal("100.00"));

        creditCard = new CreditCard();
        creditCard.setCreditCardId(CREDIT_CARD_ID);
        creditCard.setAccount(account);

        debitCard = new DebitCard();
        debitCard.setDebitCardId(DEBIT_CARD_ID);
        debitCard.setAccount(account);
    }

    private TransactionDto request(TransactionType type, String amount) {
        TransactionDto dto = new TransactionDto();
        dto.setTransactionType(type);
        dto.setTransactionAmount(new BigDecimal(amount));
        return dto;
    }

    private void stubSave() {
        when(accountDaoService.getOneById(ACCOUNT_ID)).thenReturn(Optional.of(account));
        when(transactionDaoService.create(any(Transaction.class))).thenAnswer(inv -> inv.getArgument(0));
        when(accountDaoService.update(any(Account.class))).thenAnswer(inv -> inv.getArgument(0));
    }

    @Test
    @DisplayName("Deposit with debit card increases the account balance")
    void depositWithDebitCardIncreasesBalance() {
        when(debitCardDaoService.getOneById(DEBIT_CARD_ID)).thenReturn(Optional.of(debitCard));
        stubSave();
        TransactionDto dto = request(TransactionType.DEPOSIT, "50.00");
        dto.setDebitCardId(DEBIT_CARD_ID);

        TransactionDto result = transactionService.create(dto);

        assertEquals(0, new BigDecimal("150.00").compareTo(account.getBalance()));
        assertEquals(DEBIT_CARD_ID, result.getDebitCardId());
        verify(accountDaoService).update(account);
    }

    @Test
    @DisplayName("Withdraw with credit card decreases the account balance")
    void withdrawWithCreditCardDecreasesBalance() {
        when(creditCardDaoService.getOneById(CREDIT_CARD_ID)).thenReturn(Optional.of(creditCard));
        stubSave();
        TransactionDto dto = request(TransactionType.WITHDRAW, "40.00");
        dto.setCreditCardId(CREDIT_CARD_ID);

        TransactionDto result = transactionService.create(dto);

        assertEquals(0, new BigDecimal("60.00").compareTo(account.getBalance()));
        assertEquals(CREDIT_CARD_ID, result.getCreditCardId());
    }

    @Test
    @DisplayName("Withdraw more than the balance fails and saves nothing")
    void withdrawMoreThanBalanceThrowsAndSavesNothing() {
        when(debitCardDaoService.getOneById(DEBIT_CARD_ID)).thenReturn(Optional.of(debitCard));
        when(accountDaoService.getOneById(ACCOUNT_ID)).thenReturn(Optional.of(account));
        TransactionDto dto = request(TransactionType.WITHDRAW, "500.00");
        dto.setDebitCardId(DEBIT_CARD_ID);

        assertThrows(InsufficientBalanceException.class, () -> transactionService.create(dto));

        assertEquals(0, new BigDecimal("100.00").compareTo(account.getBalance()));
        verify(transactionDaoService, never()).create(any(Transaction.class));
        verify(accountDaoService, never()).update(any(Account.class));
    }

    @Test
    @DisplayName("Unknown credit card id throws NoCreditCardFoundException")
    void unknownCreditCardThrows() {
        when(creditCardDaoService.getOneById(99L)).thenReturn(Optional.empty());
        TransactionDto dto = request(TransactionType.DEPOSIT, "10.00");
        dto.setCreditCardId(99L);

        assertThrows(NoCreditCardFoundException.class, () -> transactionService.create(dto));
    }

    @Test
    @DisplayName("Unknown debit card id throws NoDebitCardFoundException")
    void unknownDebitCardThrows() {
        when(debitCardDaoService.getOneById(99L)).thenReturn(Optional.empty());
        TransactionDto dto = request(TransactionType.DEPOSIT, "10.00");
        dto.setDebitCardId(99L);

        assertThrows(NoDebitCardFoundException.class, () -> transactionService.create(dto));
    }

    @Test
    @DisplayName("Transaction without any card throws NoCardUsedException")
    void noCardThrows() {
        TransactionDto dto = request(TransactionType.DEPOSIT, "10.00");

        assertThrows(NoCardUsedException.class, () -> transactionService.create(dto));
    }

    @Test
    @DisplayName("getOneById throws when the transaction does not exist")
    void getOneByIdNotFoundThrows() {
        when(transactionDaoService.getOneById(404L)).thenReturn(Optional.empty());

        assertThrows(TransactionIdNotFoundException.class, () -> transactionService.getOneById(404L));
    }
}
