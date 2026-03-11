package com.shedx.shedxrest.auth.dto;

public record RegisterRequest(
    String email,
    String password
){}
