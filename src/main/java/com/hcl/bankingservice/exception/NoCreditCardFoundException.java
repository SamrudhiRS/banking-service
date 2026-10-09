package com.hcl.bankingservice.exception;

public class NoCreditCardFoundException extends RuntimeException {
    public NoCreditCardFoundException(String msg) {
        super(msg);
    }

    public NoCreditCardFoundException() {
        super("Credit Card Not Found");
    }
}
