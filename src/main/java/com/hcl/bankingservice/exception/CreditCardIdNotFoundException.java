package com.hcl.bankingservice.exception;

public class CreditCardIdNotFoundException extends RuntimeException{
    private final long id;

    public CreditCardIdNotFoundException() {
        this.id = 0;
    }

    public CreditCardIdNotFoundException(long id) {
        super(String.format("CreditCard with id %d not found", id));
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
