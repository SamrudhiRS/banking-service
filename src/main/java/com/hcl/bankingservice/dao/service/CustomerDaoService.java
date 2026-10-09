package com.hcl.bankingservice.dao.service;

import com.hcl.bankingservice.model.Customer;
import com.hcl.bankingservice.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerDaoService {
    private static final Logger LOGGER= LoggerFactory.getLogger(CustomerDaoService.class.getName());

    private final CustomerRepository customerRepository;

    public CustomerDaoService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAll() {
        LOGGER.debug("Fetching all Customers from database");
        return customerRepository.findAll();
    }

    public Optional<Customer> getOneById(long id) {
        LOGGER.debug("Fetching Customer from database with id:{}",id);
        return customerRepository.findById(id);
    }

    public Customer create(Customer customer) {
        LOGGER.info("Saving Customer into database:{}",customer);
        return customerRepository.save(customer);
    }

    public Customer update(Customer customer) {
        LOGGER.info("Updating Customer into database:{}",customer);
        return customerRepository.save(customer);
    }

    public boolean delete(long id) {
        LOGGER.warn("Deleting Customer from database with id:{}",id);
        customerRepository.deleteById(id);
        return true;
    }
}
