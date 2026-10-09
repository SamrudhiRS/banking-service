package com.hcl.bankingservice.model;

import com.hcl.bankingservice.model.interfaces.Card;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class CreditCard implements Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "credit_card_id", nullable = false)
    private long creditCardId;

    // 2222222222222222222222222222222222222222222222
    @Column(name = "card_number", nullable = false)
    private long cardNumber;

    @Column(name = "cvv", nullable = false)
    private String cvv;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @OneToOne()
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
    private Account account;

    @OneToMany(mappedBy = "creditCard", cascade = CascadeType.ALL)
    private Set<Transaction> transactions = new HashSet<>();

    public CreditCard() {
    }

    public CreditCard(long creditCardId, long cardNumber, String cvv, LocalDate expiryDate) {
        this.creditCardId = creditCardId;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }

    public CreditCard(long creditCardId, long cardNumber, String cvv, LocalDate expiryDate, Account account, Set<Transaction> transactions) {
        this.creditCardId = creditCardId;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
        this.account = account;
        this.transactions = transactions;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "creditCardId=" + creditCardId +
                ", cardNumber='" + cardNumber + '\'' +
                ", cvv='" + cvv + '\'' +
                ", expiryDate=" + expiryDate +
                ", account=" + account +
                ", transactions=" + transactions +
                '}';
    }
}
