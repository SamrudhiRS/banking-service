package com.hcl.bankingservice.dao.service;

import com.hcl.bankingservice.exception.AccountIdNotFoundException;
import com.hcl.bankingservice.exception.CreditCardIdNotFoundException;
import com.hcl.bankingservice.model.Account;
import com.hcl.bankingservice.model.CreditCard;
import com.hcl.bankingservice.repository.CreditCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreditCardDaoService {
    private static final Logger LOGGER= LoggerFactory.getLogger(CreditCardDaoService.class.getName());

    private final CreditCardRepository creditCardRepository;

    public CreditCardDaoService(CreditCardRepository creditCardRepository) {
        this.creditCardRepository = creditCardRepository;
    }

    public List<CreditCard> getAll() {
        LOGGER.debug("Fetching all credit cards from database");
        return creditCardRepository.findAll();
    }

    public Optional<CreditCard> getOneById(long id) {
        LOGGER.debug("Fetching credit card with id:{}",id);
        return creditCardRepository.findById(id);
    }

    public CreditCard create(CreditCard creditCard) {
        LOGGER.info("Saving credit card to database:{}",creditCard);

        return creditCardRepository.save(creditCard);
    }

    public CreditCard update(CreditCard creditCard) {
        LOGGER.info("Updating credit card to database:{}",creditCard);
        return creditCardRepository.save(creditCard);
    }

    public boolean delete(long id) {
        LOGGER.warn("Deleting credit card from database with id:{}",id);
        Optional<CreditCard> optionalCreditCard = creditCardRepository.findById(id);
        if(optionalCreditCard.isEmpty()) {
            throw new CreditCardIdNotFoundException();
        }
        Account account = optionalCreditCard.get().getAccount();
        account.setCreditCard(null);
        CreditCard creditCard = account.getCreditCard();
        creditCardRepository.deleteById(id);
        return true;
    }
}

