package com.hcl.bankingservice.service;

import com.hcl.bankingservice.dao.service.AccountDaoService;
import com.hcl.bankingservice.dao.service.CreditCardDaoService;
import com.hcl.bankingservice.dto.AccountDto;
import com.hcl.bankingservice.dto.CreditCardDto;
import com.hcl.bankingservice.dto.CustomerDto;
import com.hcl.bankingservice.exception.CreditCardIdNotFoundException;
import com.hcl.bankingservice.exception.NoAccountFoundException;
import com.hcl.bankingservice.exception.NoCustomerFoundException;
import com.hcl.bankingservice.mapper.AccountMapper;
import com.hcl.bankingservice.mapper.CreditCardMapper;
import com.hcl.bankingservice.mapper.CustomerMapper;
import com.hcl.bankingservice.model.Account;
import com.hcl.bankingservice.model.CreditCard;
import com.hcl.bankingservice.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreditCardService {
    private static final Logger LOGGER= LoggerFactory.getLogger(CreditCardService.class.getName());


    private final CreditCardDaoService creditCardDaoService;

    private final AccountService accountService;

    private final AccountDaoService accountDaoService;

    private final CustomerService customerService;

    public CreditCardService(CreditCardDaoService creditCardDaoService, AccountService accountService, AccountDaoService accountDaoService, CustomerService customerService) {
        this.creditCardDaoService = creditCardDaoService;
        this.accountService = accountService;
        this.accountDaoService = accountDaoService;
        this.customerService = customerService;
    }

    public List<CreditCardDto> getAll() {
        LOGGER.debug("Fetching all credit cards");
        List<CreditCard> creditCardList = creditCardDaoService.getAll();

        return CreditCardMapper.toDto(creditCardList);
    }

    public CreditCardDto getOneById(long id) {
        LOGGER.debug("Fetching credit card with id:{}",id);
        Optional<CreditCard> optionalCreditCard = creditCardDaoService.getOneById(id);
        if (optionalCreditCard.isEmpty()) {
            LOGGER.error("Credit not found with id:{}",id );
            throw new CreditCardIdNotFoundException(id);
        }
        return CreditCardMapper.toDto(optionalCreditCard.get());
    }

    public boolean existsById(long id) {
        Optional<CreditCard> optionalCreditCard = creditCardDaoService.getOneById(id);
        return optionalCreditCard.isPresent();
    }

    public CreditCardDto create(CreditCardDto creditCardDto) {
        LOGGER.info("Creating credit card ");
        // check if account is exits or not
        if (!accountService.existsById(creditCardDto.getAccountId())) {
            throw new NoAccountFoundException();
        }

        Optional<Account> optionalAccount = accountDaoService.getOneById(creditCardDto.getAccountId());
        Account account = optionalAccount.get();

        if (account.getCreditCard() != null) {
            throw new RuntimeException("Credit Card Already Exits");
        }

        final CreditCard creditCard = CreditCardMapper.toEntity(creditCardDto);

        creditCard.setAccount(account);
        account.setCreditCard(creditCard);

        final CreditCard savedCreditCard = creditCardDaoService.create(creditCard);
        final Account savedAccount = accountDaoService.update(account);

        return CreditCardMapper.toDto(savedCreditCard);
    }

    public CreditCardDto update(CreditCardDto creditCardDto) {
        LOGGER.info("Updating credit card details");
        if (!accountService.existsById(creditCardDto.getAccountId())) {
            throw new NoAccountFoundException();
        }

        Optional<Account> optionalAccount = accountDaoService.getOneById(creditCardDto.getAccountId());
        Account account = optionalAccount.get();

        final CreditCard creditCard = CreditCardMapper.toEntity(creditCardDto);
        creditCard.setAccount(account);

        final CreditCard updatedCreditCard = creditCardDaoService.update(creditCard);

        return CreditCardMapper.toDto(updatedCreditCard);
    }

    public boolean delete(long id) {
        LOGGER.warn("Deleting credit card with id:{}",id);
        return creditCardDaoService.delete(id);
    }
}
