package com.chuan.courcelts.advices.exceptions;

public class StatusStudyNotFoundException extends RuntimeException{
    public StatusStudyNotFoundException(int id){
        super("Could not found status: " + id);
    }
}
