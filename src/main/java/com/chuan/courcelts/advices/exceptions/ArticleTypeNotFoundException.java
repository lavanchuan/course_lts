package com.chuan.courcelts.advices.exceptions;

public class ArticleTypeNotFoundException extends RuntimeException{
    public ArticleTypeNotFoundException(int id){
        super("Could not found article type: " + id);
    }
}
