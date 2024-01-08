package com.chuan.courcelts.advices.exceptions;

public class AuthorityNotFoundException extends RuntimeException{
    public AuthorityNotFoundException(int id){
        super("Could not found authority: " + id);
    }
}
