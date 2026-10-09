package com.hcl.bankingservice.service;

import com.hcl.bankingservice.dao.service.AccountDaoService;
import com.hcl.bankingservice.dao.service.DebitCardDaoService;
import com.hcl.bankingservice.dto.AccountDto;
import com.hcl.bankingservice.dto.CreditCardDto;
import com.hcl.bankingservice.dto.CustomerDto;
import com.hcl.bankingservice.dto.DebitCardDto;
import com.hcl.bankingservice.exception.DebitCardIdNotFoundException;
import com.hcl.bankingservice.exception.NoAccountFoundException;
import com.hcl.bankingservice.mapper.AccountMapper;
import com.hcl.bankingservice.mapper.CreditCardMapper;
import com.hcl.bankingservice.mapper.CustomerMapper;
import com.hcl.bankingservice.mapper.DebitCardMapper;
import com.hcl.bankingservice.model.Account;
import com.hcl.bankingservice.model.CreditCard;
import com.hcl.bankingservice.model.Customer;
import com.hcl.bankingservice.model.DebitCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DebitCardService {
    private static final Logger LOGGER= LoggerFactory.getLogger(DebitCardService.class.getName());


    private final DebitCardDaoService debitCardDaoService;

    private final CustomerService customerService;

    private final AccountService accountService;

    private final AccountDaoService accountDaoService;

    public DebitCardService(DebitCardDaoService debitCardDaoService, CustomerService customerService, AccountService accountService, AccountDaoService accountDaoService) {
        this.debitCardDaoService = debitCardDaoService;
        this.customerService = customerService;
        this.accountService = accountService;
        this.accountDaoService = accountDaoService;
    }

    public List<DebitCardDto> getAll() {
        LOGGER.debug("Fetching all Debit cards");
        List<DebitCard> debitCardList = debitCardDaoService.getAll();

        return DebitCardMapper.toDto(debitCardList);
    }

    public DebitCardDto getOneById(long id) {
        LOGGER.debug("Fetching Debit card with id:{}",id);
        Optional<DebitCard> optionalDebitCard = debitCardDaoService.getOneById(id);
        if (optionalDebitCard.isEmpty()) {
            LOGGER.error("Credit not found with id:{}",id );
            throw new DebitCardIdNotFoundException(id);
        }
        return DebitCardMapper.toDto(optionalDebitCard.get());
    }

    public boolean existsById(long id) {
        Optional<DebitCard> optionalCreditCard = debitCardDaoService.getOneById(id);
        return optionalCreditCard.isPresent();
    }

    public DebitCardDto create(DebitCardDto debitCardDto) {
        LOGGER.info("Creating Debit card ");
        // check if account is exits or not
        if (!accountService.existsById(debitCardDto.getAccountId())) {
            LOGGER.error("Account not found with id:{}",debitCardDto.getAccountId());
            throw new NoAccountFoundException();
        }

        Optional<Account> optionalAccount = accountDaoService.getOneById(debitCardDto.getAccountId());
        Account account = optionalAccount.get();

        if (account.getDebitCard() != null) {
            throw new RuntimeException("Debit Card Already Exits");
        }

        final DebitCard debitCard = DebitCardMapper.toEntity(debitCardDto);

        debitCard.setAccount(account);
        account.setDebitCard(debitCard);

        final DebitCard savedDebitCard = debitCardDaoService.create(debitCard);
        final Account savedAccount = accountDaoService.update(account);

        return DebitCardMapper.toDto(savedDebitCard);
    }

    public DebitCardDto update(DebitCardDto debitCardDto) {
        LOGGER.info("Updating Debit card ");
        if (!accountService.existsById(debitCardDto.getAccountId())) {
            LOGGER.error("Account not found with id:{}",debitCardDto.getAccountId());
            throw new NoAccountFoundException();
        }

        Optional<Account> optionalAccount = accountDaoService.getOneById(debitCardDto.getAccountId());
        Account account = optionalAccount.get();

        final DebitCard debitCard = DebitCardMapper.toEntity(debitCardDto);
        debitCard.setAccount(account);

        final DebitCard updatedDebitCard = debitCardDaoService.update(debitCard);

        return DebitCardMapper.toDto(updatedDebitCard);
    }

    public boolean delete(long id) {
        LOGGER.warn("Deleting Debit card with id:{}",id);
        return debitCardDaoService.delete(id);
    }
}
