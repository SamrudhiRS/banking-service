package com.hcl.bankingservice.exception;

public class CustomerIdNotFoundException extends RuntimeException{

    private final long id;

    public CustomerIdNotFoundException() {
        this.id = 0;
    }

    public CustomerIdNotFoundException(long id) {
        super(String.format("Customer with id %d not found", id));
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
