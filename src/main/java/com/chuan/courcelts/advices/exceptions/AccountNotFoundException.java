package com.chuan.courcelts.advices.exceptions;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(int id){
        super("Could not found account: " + id);
    }
}
