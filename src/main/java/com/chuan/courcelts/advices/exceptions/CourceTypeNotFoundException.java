package com.chuan.courcelts.advices.exceptions;

public class CourceTypeNotFoundException extends RuntimeException {
    public CourceTypeNotFoundException(int courceTypeId) {
        super("Could not found Cource Type: " + courceTypeId);
    }
}
