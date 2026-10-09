package com.hcl.bankingservice.exception;

public class NoCustomerFoundException extends RuntimeException{
    public NoCustomerFoundException() {
        super("Customer not found");

    }

}
