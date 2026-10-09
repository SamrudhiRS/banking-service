package com.hcl.bankingservice.dao.service;

import com.hcl.bankingservice.exception.CreditCardIdNotFoundException;
import com.hcl.bankingservice.model.Account;
import com.hcl.bankingservice.model.CreditCard;
import com.hcl.bankingservice.model.DebitCard;
import com.hcl.bankingservice.repository.DebitCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DebitCardDaoService {
    private static final Logger LOGGER= LoggerFactory.getLogger(DebitCardDaoService.class.getName());

    private final DebitCardRepository debitCardRepository;

    public DebitCardDaoService(DebitCardRepository debitCardRepository) {
        this.debitCardRepository = debitCardRepository;
    }

    public List<DebitCard> getAll() {
        LOGGER.debug("Fetching all Debit cards from database");

        return debitCardRepository.findAll();
    }

    public Optional<DebitCard> getOneById(long id) {
        LOGGER.debug("Fetching Debit card from database with id:{}",id);
        return debitCardRepository.findById(id);
    }

    public DebitCard create(DebitCard debitCard) {
        LOGGER.info("Saving Debit card into database:{}",debitCard);
        return debitCardRepository.save(debitCard);
    }

    public DebitCard update(DebitCard debitCard) {
        LOGGER.info("Updating Debit card into database:{}",debitCard);
        return debitCardRepository.save(debitCard);
    }

    public boolean delete(long id) {
        LOGGER.warn("Deleting Debit card from database with id:{}",id);
        // Use here getOneById
        Optional<DebitCard> optionalDebitCard = debitCardRepository.findById(id);
        if(optionalDebitCard.isEmpty()) {
            throw new CreditCardIdNotFoundException();
        }
        DebitCard debitCard = optionalDebitCard.get();
        Account account = debitCard.getAccount();
        account.setDebitCard(null);
        debitCardRepository.deleteById(id);
        return true;
    }
}
