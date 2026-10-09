package com.hcl.bankingservice.dto;

import com.hcl.bankingservice.enums.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionDto {

    private long transactionId;

    private LocalDate transactionDate;

    @NotNull
    @Positive
    private BigDecimal transactionAmount;

    @NotNull
    private TransactionType transactionType;

    private long creditCardId;

    private long debitCardId;

    public TransactionDto() {
    }

    public TransactionDto(long transactionId, LocalDate transactionDate, BigDecimal transactionAmount, TransactionType transactionType) {
        this.transactionId = transactionId;
        this.transactionDate = transactionDate;
        this.transactionAmount = transactionAmount;
        this.transactionType = transactionType;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(long creditCardId) {
        this.creditCardId = creditCardId;
    }

    public long getDebitCardId() {
        return debitCardId;
    }

    public void setDebitCardId(long debitCardId) {
        this.debitCardId = debitCardId;
    }
}
