package com.shedx.shedxrest.auth.dto;

public class UserAlredyExists extends RuntimeException {
    public UserAlredyExists(String message){
        super(message);
    }
}
