package com.hcl.bankingservice.exception;

public class NoTransactionFoundException extends RuntimeException {
    public NoTransactionFoundException(String msg) {
        super(msg);
    }

    public NoTransactionFoundException() {
        super("Transaction  Not Found");
    }


}
