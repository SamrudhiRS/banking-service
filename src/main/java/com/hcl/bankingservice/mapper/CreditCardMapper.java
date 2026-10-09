package com.hcl.bankingservice.mapper;

import com.hcl.bankingservice.dto.CreditCardDto;
import com.hcl.bankingservice.dto.TransactionDto;
import com.hcl.bankingservice.model.CreditCard;
import com.hcl.bankingservice.model.Transaction;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class CreditCardMapper {
    public static List<CreditCardDto> toDto(List<CreditCard> creditCardList) {
        return creditCardList.stream()
                .map(creditCard -> toDto(creditCard))
                .collect(Collectors.toList());
    }

    public static CreditCardDto toDto(CreditCard creditCard) {
        CreditCardDto creditCardDto = new CreditCardDto();
        creditCardDto.setCreditCardId(creditCard.getCreditCardId());
        creditCardDto.setCardNumber(creditCard.getCardNumber());
        creditCardDto.setCvv(creditCard.getCvv());
        creditCardDto.setExpiryDate(creditCard.getExpiryDate());

        creditCardDto.setAccountId(creditCard.getAccount().getAccountId());

        creditCardDto.setTransactionDtos(TransactionMapper.toDto(creditCard.getTransactions()));

        return creditCardDto;
    }

    public static long generatingUniqueId() {
        String generatedUUID = String.format("%010d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
//        System.out.println(generatedUUID);
        long uniqueId = Long.parseLong(generatedUUID.substring(generatedUUID.length()-10));
//        System.out.println(uniqueId);
        return uniqueId;
    }

    public static CreditCard toEntity(CreditCardDto creditCardDto) {
        CreditCard creditCard = new CreditCard();
        creditCard.setCreditCardId(creditCardDto.getCreditCardId());

        // Here I am adding generated ID
        creditCard.setCardNumber(generatingUniqueId());

        creditCard.setCvv(creditCardDto.getCvv());
        creditCard.setExpiryDate(creditCardDto.getExpiryDate());

        if (creditCardDto.getTransactionDtos() != null && creditCardDto.getTransactionDtos().size() > 0) {
            Set<TransactionDto> transactionDtos = creditCardDto.getTransactionDtos();
            Set<Transaction> transactionSet = TransactionMapper.toEntity(transactionDtos);

            // Check here if it is creating any bug
            creditCard.getTransactions().addAll(transactionSet);
            transactionSet.forEach(transaction -> transaction.setCreditCard(creditCard));
        }

        return creditCard;
    }
}
