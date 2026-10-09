package com.hcl.bankingservice.mapper;

import com.hcl.bankingservice.dto.AccountDto;
import com.hcl.bankingservice.dto.CustomerDto;
import com.hcl.bankingservice.exception.MissingAccountException;
import com.hcl.bankingservice.model.Account;
import com.hcl.bankingservice.model.Customer;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomerMapper {

    public static CustomerDto toDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId(customer.getCustomerId());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setDateOfBirth(customer.getDateOfBirth());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        customerDto.setAccountDtos(AccountMapper.toDto(customer.getAccounts()));
        return customerDto;
    }

    public static List<CustomerDto> toDto(List<Customer> customerList) {
        return customerList
                .stream()
                .map(customer -> toDto(customer))
                .collect(Collectors.toList());
    }

    public static Customer toEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setCustomerId(customerDto.getCustomerId());
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setEmail(customerDto.getEmail());
        customer.setDateOfBirth(customerDto.getDateOfBirth());
        customer.setPhoneNumber(customerDto.getPhoneNumber());

        // Account Dto
        if (customerDto.getAccountDtos() == null) {
            throw new MissingAccountException();
        }
        Set<AccountDto> accountDtos = customerDto.getAccountDtos();
        Set<Account> accountSet = AccountMapper.toEntity(accountDtos);

        customer.setAccounts(accountSet);
        accountSet.forEach(account -> account.setCustomer(customer));

        return customer;
    }

}
