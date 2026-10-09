package com.hcl.bankingservice.exception;

public class DebitCardIdNotFoundException extends RuntimeException{
    private final long id;

    public DebitCardIdNotFoundException() {
        this.id = 0;
    }

    public DebitCardIdNotFoundException(long id) {
        super(String.format("DebitCard with id %d not found", id));
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
