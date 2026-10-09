package com.hcl.bankingservice.mapper;

import com.hcl.bankingservice.dto.CreditCardDto;
import com.hcl.bankingservice.dto.DebitCardDto;
import com.hcl.bankingservice.dto.TransactionDto;
import com.hcl.bankingservice.model.CreditCard;
import com.hcl.bankingservice.model.DebitCard;
import com.hcl.bankingservice.model.Transaction;

import java.math.BigInteger;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class DebitCardMapper {

    public static List<DebitCardDto> toDto(List<DebitCard> debitCardList) {
        return debitCardList.stream()
                .map(debitCard -> toDto(debitCard))
                .collect(Collectors.toList());
    }

    public static DebitCardDto toDto(DebitCard debitCard) {
        DebitCardDto debitCardDto = new DebitCardDto();
        debitCardDto.setDebitCardId(debitCard.getDebitCardId());
        debitCardDto.setCardNumber(debitCard.getCardNumber());
        debitCardDto.setCvv(debitCard.getCvv());
        debitCardDto.setExpiryDate(debitCard.getExpiryDate());

        debitCardDto.setAccountId(debitCard.getAccount().getAccountId());

        debitCardDto.setTransactionDtos(TransactionMapper.toDto(debitCard.getTransactions()));

        return debitCardDto;
    }

    public static long generatingUniqueId() {
        long unique_no = System.currentTimeMillis();
//        System.out.println(unique_no);
        return unique_no;
    }

    public static DebitCard toEntity(DebitCardDto debitCardDto) {
        DebitCard debitCard = new DebitCard();
        debitCard.setDebitCardId(debitCardDto.getDebitCardId());

        // Here I am adding generated ID
        debitCard.setCardNumber(generatingUniqueId());

        debitCard.setCvv(debitCardDto.getCvv());
        debitCard.setExpiryDate(debitCardDto.getExpiryDate());

        if (debitCardDto.getTransactionDtos() != null && debitCardDto.getTransactionDtos().size() > 0) {
            Set<TransactionDto> transactionDtos = debitCardDto.getTransactionDtos();
            Set<Transaction> transactionSet = TransactionMapper.toEntity(transactionDtos);

            // Check here if it is creating any bug
            debitCard.getTransactions().addAll(transactionSet);
            transactionSet.forEach(transaction -> transaction.setDebitCard(debitCard));
        }

        return debitCard;
    }

}
