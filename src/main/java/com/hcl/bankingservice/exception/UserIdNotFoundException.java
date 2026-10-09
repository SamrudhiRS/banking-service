package com.hcl.bankingservice.exception;

public class UserIdNotFoundException extends RuntimeException{

    private final long id;

    public UserIdNotFoundException() {
        this.id = 0;
    }

    public UserIdNotFoundException(long id) {
        super(String.format("Customer with id %d not found", id));
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
