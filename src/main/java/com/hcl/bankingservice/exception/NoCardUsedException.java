package com.hcl.bankingservice.exception;

public class NoCardUsedException extends RuntimeException{
    public NoCardUsedException() {
        super("Please use any card!");
    }

    public NoCardUsedException(String message) {
        super(message);
    }
}
