package com.hcl.bankingservice.service;

import com.hcl.bankingservice.dao.service.CustomerDaoService;
import com.hcl.bankingservice.dto.CustomerDto;
import com.hcl.bankingservice.enums.AccountType;
import com.hcl.bankingservice.exception.CustomerIdNotFoundException;
import com.hcl.bankingservice.exception.DuplicateAccountTypeException;
import com.hcl.bankingservice.mapper.CustomerMapper;
import com.hcl.bankingservice.model.Account;
import com.hcl.bankingservice.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class.getName());


    private final CustomerDaoService customerDaoService;

    public CustomerService(CustomerDaoService customerDaoService) {
        this.customerDaoService = customerDaoService;
    }

    public List<CustomerDto> getAll() {
        LOGGER.debug("Fetching Customers");
        List<Customer> customerList = customerDaoService.getAll();

        return CustomerMapper.toDto(customerList);
    }

    public CustomerDto getOneById(long id) {
        LOGGER.debug("Fetching Customer with id:{}",id);
        Optional<Customer> optionalCustomer = customerDaoService.getOneById(id);
        if (optionalCustomer.isEmpty()) {
            LOGGER.error("No Customer with id:{}",id);
            throw new CustomerIdNotFoundException(id);
        }
        return CustomerMapper.toDto(optionalCustomer.get());
    }

    public boolean existsById(long id) {
        Optional<Customer> optionalCustomer = customerDaoService.getOneById(id);
        return optionalCustomer.isPresent();
    }

    public CustomerDto create(CustomerDto customerDto) {
        LOGGER.info("Creating Customer");
        final Customer customer = CustomerMapper.toEntity(customerDto);

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

        if (countOfSavingAccount > 1 || countOfCurrentAccount > 1) {
            LOGGER.error("Account of same type exists with Customer");
            throw new DuplicateAccountTypeException();
        }

        final Customer savedCustomer = customerDaoService.create(customer);
        return CustomerMapper.toDto(savedCustomer);
    }

    public CustomerDto update(CustomerDto customerDto) {
        LOGGER.info("Updating Customer");
        final Customer customer = CustomerMapper.toEntity(customerDto);

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

        if (countOfSavingAccount > 1 || countOfCurrentAccount > 1) {
            LOGGER.error("Account of same type exists with Customer");
            throw new DuplicateAccountTypeException();
        }

        final Customer updatedCustomer = customerDaoService.update(customer);
        return CustomerMapper.toDto(updatedCustomer);
    }

    public boolean delete(long id) {
        LOGGER.warn("Deleting Customer with id:{}",id);
        return customerDaoService.delete(id);
    }
}
