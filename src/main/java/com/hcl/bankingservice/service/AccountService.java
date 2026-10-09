package com.hcl.bankingservice.service;

import com.hcl.bankingservice.dao.service.AccountDaoService;
import com.hcl.bankingservice.dao.service.CustomerDaoService;
import com.hcl.bankingservice.dto.AccountDto;
import com.hcl.bankingservice.dto.CustomerDto;
import com.hcl.bankingservice.enums.AccountType;
import com.hcl.bankingservice.exception.AccountIdNotFoundException;
import com.hcl.bankingservice.exception.DuplicateAccountTypeException;
import com.hcl.bankingservice.exception.NoCustomerFoundException;
import com.hcl.bankingservice.mapper.AccountMapper;
import com.hcl.bankingservice.mapper.CustomerMapper;
import com.hcl.bankingservice.model.Account;
import com.hcl.bankingservice.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class AccountService {
    private static final Logger LOGGER= LoggerFactory.getLogger(AccountService.class.getName());

    private final AccountDaoService accountDaoService;

    private final CustomerService customerService;

    private final CustomerDaoService customerDaoService;

    public AccountService(AccountDaoService accountDaoService, CustomerService customerService, CustomerDaoService customerDaoService) {
        this.accountDaoService = accountDaoService;
        this.customerService = customerService;
        this.customerDaoService = customerDaoService;
    }

    public List<AccountDto> getAll() {
        LOGGER.debug("Fetching all accounts");
        List<Account> accountList = accountDaoService.getAll();

        return AccountMapper.toDto(accountList);
    }

    public boolean existsById(long id) {
        Optional<Account> optionalAccount = accountDaoService.getOneById(id);
        return optionalAccount.isPresent();
    }

    public AccountDto getOneById(long id) {
        LOGGER.debug("Retrieving account by Id:{}",id);
        Optional<Account> optionalAccount = accountDaoService.getOneById(id);
        if (optionalAccount.isEmpty()) {
            LOGGER.error("No account with given id:{}",id);
            throw new AccountIdNotFoundException(id);
        }
        return AccountMapper.toDto(optionalAccount.get());
    }

    public AccountDto create(AccountDto accountDto) {
        LOGGER.info("Creating account :{}",accountDto);
        // check if customer is exits or not
        if (!customerService.existsById(accountDto.getCustomerId())) {
            LOGGER.error("No customer with given id:{}",accountDto.getCustomerId());
            throw new NoCustomerFoundException();
        }

        Optional<Customer> optionalCustomer = customerDaoService.getOneById(accountDto.getCustomerId());
        Customer customer = optionalCustomer.get();

        // Checking for Typeof Account
        int countOfSavingAccount = 0;
        int countOfCurrentAccount = 0;
        Set<Account> accounts = customer.getAccounts();
        for (Account account: accounts) {
            if (account.getAccountType().equals(AccountType.SAVING)) {
                countOfSavingAccount++;
            }
            else if (account.getAccountType().equals(AccountType.CURRENT)) {
                countOfCurrentAccount++;
            }
        }

        final Account account = AccountMapper.toEntity(accountDto);

        if (account.getAccountType().equals(AccountType.SAVING) && countOfSavingAccount > 0) {
            LOGGER.error("Saving account already exists");
            throw new DuplicateAccountTypeException("Saving account already exits.");

        }
        if (account.getAccountType().equals(AccountType.CURRENT) && countOfCurrentAccount > 0) {
            LOGGER.error("Current account already exists");
            throw new DuplicateAccountTypeException("Current account already exits.");
        }

        account.setCustomer(customer);
        customer.getAccounts().add(account);
        final Account accountSavedOne = accountDaoService.create(account);
        final Customer updatedCustomer = customerDaoService.update(customer);

        return AccountMapper.toDto(accountSavedOne);
    }

    public AccountDto update(AccountDto accountDto) {
        if (!customerService.existsById(accountDto.getCustomerId())) {
            LOGGER.error("No customer with given id:{}",accountDto.getCustomerId());
            throw new NoCustomerFoundException();
        }

        Optional<Customer> optionalCustomer = customerDaoService.getOneById(accountDto.getCustomerId());
        Customer customer = optionalCustomer.get();

        final Account account = AccountMapper.toEntity(accountDto);
        account.setCustomer(customer);

        final Account updatedAccount = accountDaoService.update(account);

        return AccountMapper.toDto(updatedAccount);
    }

    public boolean delete(long id) {
        LOGGER.warn("Deleting account with id:{}",id);
        return accountDaoService.delete(id);
    }
}
