package com.chuan.courcelts.advices.exceptions;

public class CourceNotFoundException extends RuntimeException {
    public CourceNotFoundException(int id) {
        super("Could not found cource: " + id);
    }
}
