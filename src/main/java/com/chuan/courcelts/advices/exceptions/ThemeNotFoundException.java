package com.chuan.courcelts.advices.exceptions;

public class ThemeNotFoundException extends RuntimeException{
    public ThemeNotFoundException(int id){
        super("Could not found theme: " + id);
    }
}
