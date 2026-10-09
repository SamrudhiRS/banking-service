package com.hcl.bankingservice.mapper;

import com.hcl.bankingservice.dto.TransactionDto;
import com.hcl.bankingservice.model.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TransactionMapper {

    public static TransactionDto toDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setTransactionId(transaction.getTransactionId());
        transactionDto.setTransactionDate(transaction.getTransactionDate());
        transactionDto.setTransactionAmount(transaction.getTransactionAmount());
        transactionDto.setTransactionType(transaction.getTransactionType());

        // Update here to get proper response

        if (transaction.getCreditCard() != null)
            transactionDto.setCreditCardId(transaction.getCreditCard().getCreditCardId());
        if (transaction.getDebitCard() != null)
            transactionDto.setDebitCardId(transaction.getDebitCard().getDebitCardId());

        return transactionDto;
    }

    public static List<TransactionDto> toDto(List<Transaction> transactionList) {
        return transactionList.stream()
                .map(transaction -> toDto(transaction))
                .collect(Collectors.toList());
    }

    public static Set<TransactionDto> toDto(Set<Transaction> transactionSet) {
        return transactionSet.stream()
                .map(transaction -> toDto(transaction))
                .collect(Collectors.toSet());
    }

    public static Transaction toEntity(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionDto.getTransactionId());
        transaction.setTransactionAmount(transactionDto.getTransactionAmount());
        // Here we can add the by using LocalDate.now() function
        transaction.setTransactionDate(LocalDate.now());
        transaction.setTransactionType(transactionDto.getTransactionType());
        return transaction;
    }

    // New Code
    public static Set<Transaction> toEntity(Set<TransactionDto> transactionSet) {
        return transactionSet.stream()
                .map(transactionDto -> toEntity(transactionDto))
                .collect(Collectors.toSet());
    }

}
