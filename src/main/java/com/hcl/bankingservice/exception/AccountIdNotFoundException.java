package com.hcl.bankingservice.exception;

public class AccountIdNotFoundException extends RuntimeException{
    private final long id;

    public AccountIdNotFoundException() {
        this.id = 0;
    }

    public AccountIdNotFoundException(long id) {
        super(String.format("Account with id %d not found", id));
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
