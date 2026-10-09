package com.hcl.bankingservice.exception;

public class NoAccountFoundException extends RuntimeException {
    public NoAccountFoundException(String msg) {
        super(msg);
    }

    public NoAccountFoundException() {
        super("Account Not Found");
    }
}
