package com.hcl.bankingservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public class DebitCardDto {

    private long debitCardId;

    // 2222222222222222222222222222222222222222222222222222222222222222
    private long cardNumber;

    @NotNull
    private String cvv;

    @NotNull
    private LocalDate expiryDate;

    private long accountId; // For reference to accoutDto

//    private long customerId; // For reference to customerDto

    @Valid
    private Set<TransactionDto> transactionDtos;

    public DebitCardDto() {
    }

    public DebitCardDto(long debitCardId, long cardNumber, String cvv, LocalDate expiryDate) {
        this.debitCardId = debitCardId;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }

    public long getDebitCardId() {
        return debitCardId;
    }

    public void setDebitCardId(long debitCardId) {
        this.debitCardId = debitCardId;
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
        return "DebitCardDto{" +
                "debitCardId=" + debitCardId +
                ", cardNumber='" + cardNumber + '\'' +
                ", cvv='" + cvv + '\'' +
                ", expiryDate=" + expiryDate +
                ", accountId=" + accountId +
                ", transactionDtos=" + transactionDtos +
                '}';
    }
}
