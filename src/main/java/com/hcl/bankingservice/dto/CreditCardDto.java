package com.hcl.bankingservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public class CreditCardDto {

    private long creditCardId;

    // 2222222222222222222222222222222222222222222222222222222222222222222222222222
    private long cardNumber;

    @NotEmpty
    private String cvv;

    @NotNull
    private LocalDate expiryDate;

    private long accountId; // For reference to accoutDto

    @Valid
    private Set<TransactionDto> transactionDtos;

    public CreditCardDto() {
    }

    public CreditCardDto(long creditCardId, long cardNumber, String cvv, LocalDate expiryDate) {
        this.creditCardId = creditCardId;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }

    public long getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(long creditCardId) {
        this.creditCardId = creditCardId;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public Set<TransactionDto> getTransactionDtos() {
        return transactionDtos;
    }

    public void setTransactionDtos(Set<TransactionDto> transactionDtos) {
        this.transactionDtos = transactionDtos;
    }

    @Override
    public String toString() {
        return "CreditCardDto{" +
                "creditCardId=" + creditCardId +
                ", cardNumber='" + cardNumber + '\'' +
                ", cvv='" + cvv + '\'' +
                ", expiryDate=" + expiryDate +
                ", accountId=" + accountId +
                ", transactionDtos=" + transactionDtos +
                '}';
    }
}
