package com.chuan.courcelts.advices.exceptions;

public class RegisterNotFoundException extends RuntimeException{
    public RegisterNotFoundException(int id){
        super("Could not found register: " + id);
    }
}
