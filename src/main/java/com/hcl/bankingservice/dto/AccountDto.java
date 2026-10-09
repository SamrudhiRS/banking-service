package com.hcl.bankingservice.dto;

import com.hcl.bankingservice.enums.AccountType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AccountDto {

    private long accountId;

    // 222222222222222222222222222222222222222222222222222222222222222222222222222
    private long accountNumber;

    @NotNull
    @PositiveOrZero
    private BigDecimal balance = BigDecimal.ZERO;


    private LocalDate dateOfOpening;

    @NotNull
    private AccountType accountType;

    private long customerId; // For reference to customerDto

    @Valid
    private CreditCardDto creditCardDto;

    @Valid
    private DebitCardDto debitCardDto;

    public AccountDto() {
    }

    public AccountDto(long accountId, long accountNumber, BigDecimal balance, AccountType accountType) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = accountType;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public LocalDate getDateOfOpening() {
        return dateOfOpening;
    }

    public void setDateOfOpening(LocalDate dateOfOpening) {
        this.dateOfOpening = dateOfOpening;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public CreditCardDto getCreditCardDto() {
        return creditCardDto;
    }

    public void setCreditCardDto(CreditCardDto creditCardDto) {
        this.creditCardDto = creditCardDto;
    }

    public DebitCardDto getDebitCardDto() {
        return debitCardDto;
    }

    public void setDebitCardDto(DebitCardDto debitCardDto) {
        this.debitCardDto = debitCardDto;
    }
}
