package com.hcl.bankingservice.dao.service;

import com.hcl.bankingservice.exception.AccountIdNotFoundException;
import com.hcl.bankingservice.model.Account;
import com.hcl.bankingservice.model.Customer;
import com.hcl.bankingservice.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountDaoService {
    private static final Logger LOGGER= LoggerFactory.getLogger(AccountDaoService.class.getName());


    private final AccountRepository accountRepository;
    private final CustomerDaoService customerDaoService;

    public AccountDaoService(AccountRepository accountRepository, CustomerDaoService customerDaoService) {
        this.accountRepository = accountRepository;
        this.customerDaoService = customerDaoService;
    }

    public List<Account> getAll() {
        LOGGER.debug("Fetching all accounts from database");
        return accountRepository.findAll();
    }

    public Optional<Account> getOneById(long id) {
        LOGGER.debug("Retrieving account from database with Id:{}",id);
        return accountRepository.findById(id);
    }

    public Account create(Account account) {
        LOGGER.info("Saving account to database :{}",account);
        return accountRepository.save(account);
    }

    public Account update(Account account) {
        LOGGER.info("Updating account to database :{}",account);
        return accountRepository.save(account);
    }

    public boolean delete(long id) {
        LOGGER.warn("Deleting account with id:{}",id);
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if(optionalAccount.isEmpty()) {
            throw new AccountIdNotFoundException();
        }
        Account account = optionalAccount.get();
        Customer customer = account.getCustomer();

        if (customer.getAccounts().size() > 1)
            customer.getAccounts().remove(account);
        else {
            LOGGER.error("Customer has only one account.Delete Customer to erase all account data");
            throw new RuntimeException("Only one account");
        }

        accountRepository.deleteById(id);
        return true;
    }
}
