package com.hcl.bankingservice.model;

import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import com.hcl.bankingservice.enums.AccountType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id", nullable = false)
    private long accountId;

    // 2222222222222222222222222222222222222222222222
    @Column(name = "account_number", nullable = false)
    private long accountNumber;

    @Column(name = "balance", nullable = false, precision = 19, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "date_of_opening")
    private LocalDate dateOfOpening;
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountType accountType;

    @ManyToOne()
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    private Customer customer;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private CreditCard creditCard;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private DebitCard debitCard;

    public Account() {
    }

    public Account(long accountId, long accountNumber, BigDecimal balance, LocalDate dateOfOpening, AccountType accountType) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.dateOfOpening = dateOfOpening;
        this.accountType = accountType;
    }

    public Account(long accountId, long accountNumber, BigDecimal balance, LocalDate dateOfOpening, AccountType accountType, Customer customer, CreditCard creditCard, DebitCard debitCard) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.dateOfOpening = dateOfOpening;
        this.accountType = accountType;
        this.customer = customer;
        this.creditCard = creditCard;
        this.debitCard = debitCard;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public DebitCard getDebitCard() {
        return debitCard;
    }

    public void setDebitCard(DebitCard debitCard) {
        this.debitCard = debitCard;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", dateOfOpening=" + dateOfOpening +
                ", accountType=" + accountType +
//                ", customer=" + customer +
//                ", creditCard=" + creditCard +
//                ", debitCard=" + debitCard +
                '}';
    }
}
