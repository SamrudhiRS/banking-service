package com.hcl.bankingservice.exception;

public class DuplicateAccountTypeException extends RuntimeException{
    public DuplicateAccountTypeException() {
        super("Type of Account should be unique.");
    }

    public DuplicateAccountTypeException(String message) {
        super(message);
    }
}
