package com.shedx.shedxrest.auth.dto;

public class ErrorReponse {
    private String message;
    public ErrorReponse(String message){
        this.message = message;
    }
    public String getMessage(){
        return message;
    }
}
