package com.chuan.courcelts.advices.exceptions;

public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(int id){
        super("Could not found student: " + id);
    }
}
