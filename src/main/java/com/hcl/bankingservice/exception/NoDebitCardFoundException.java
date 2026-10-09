package com.hcl.bankingservice.exception;

public class NoDebitCardFoundException extends RuntimeException {
    public NoDebitCardFoundException(String msg) {
        super(msg);
    }
    public NoDebitCardFoundException() {
        super("Debit Card Not Found");
    }

}