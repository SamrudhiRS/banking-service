package com.hcl.bankingservice.dao.service;

import com.hcl.bankingservice.model.Account;
import com.hcl.bankingservice.model.CreditCard;
import com.hcl.bankingservice.model.Customer;
import com.hcl.bankingservice.model.Transaction;
import com.hcl.bankingservice.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionDaoService {
    private static final Logger LOGGER= LoggerFactory.getLogger(TransactionDaoService.class.getName());


    private final TransactionRepository transactionRepository;

    private final CreditCardDaoService creditCardDaoService;

    public TransactionDaoService(TransactionRepository transactionRepository, CreditCardDaoService creditCardDaoService) {
        this.transactionRepository = transactionRepository;
        this.creditCardDaoService = creditCardDaoService;
    }

    public List<Transaction> getAll() {
        LOGGER.debug("Fetching all Transactions from database");
        return transactionRepository.findAll();
    }

    public Optional<Transaction> getOneById(long id) {
        LOGGER.debug("Fetching Transactions from database with id:{}",id);
        return transactionRepository.findById(id);
    }

    public Transaction create(Transaction transaction) {
        LOGGER.info("Saving Transaction :{}",transaction );
        return transactionRepository.save(transaction);
    }

    // No need of this method
    public Transaction update(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public boolean delete(long id) {
        LOGGER.warn("Deleting Transaction with id:{}",id);
        transactionRepository.deleteById(id);
        return true;
    }



}
