package com.hcl.bankingservice.exception;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException() {
        super("Balance is not sufficient to make transaction");
    }

}
