package com.hcl.bankingservice.exception;

public class MissingAccountException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "Account is required";
    public MissingAccountException() {
        super(DEFAULT_MESSAGE);
    }
    public MissingAccountException(String msg) {
        super(msg);
    }
}
