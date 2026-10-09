package com.hcl.bankingservice.model;

import com.hcl.bankingservice.model.interfaces.Card;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class DebitCard implements Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "debit_card_id")
    private long debitCardId;

    @Column(name = "card_number", nullable = false)
    private long cardNumber;

    @Column(name = "cvv", nullable = false)
    private String cvv;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @OneToOne()
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
    private Account account;

    @OneToMany(mappedBy = "debitCard", cascade = CascadeType.ALL)
    private Set<Transaction> transactions = new HashSet<>();

    public DebitCard() {
    }

    public DebitCard(long debitCardId, long cardNumber, String cvv, LocalDate expiryDate) {
        this.debitCardId = debitCardId;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }

    public DebitCard(long debitCardId, long cardNumber, String cvv, LocalDate expiryDate, Account account, Set<Transaction> transactions) {
        this.debitCardId = debitCardId;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
        this.account = account;
        this.transactions = transactions;
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
        return "DebitCard{" +
                "debitCardId=" + debitCardId +
                ", cardNumber='" + cardNumber + '\'' +
                ", cvv='" + cvv + '\'' +
                ", expiryDate=" + expiryDate +
                ", account=" + account +
                ", transactions=" + transactions +
                '}';
    }
}
