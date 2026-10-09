package com.hcl.bankingservice.exception;

public class TransactionIdNotFoundException  extends RuntimeException{
    private final long id;

    public TransactionIdNotFoundException() {
        this.id = 0;
    }

    public TransactionIdNotFoundException(long id) {
        super(String.format("Transaction with id %d not found", id));
        this.id = id;
    }

    public long getId() {
        return id;
    }
}