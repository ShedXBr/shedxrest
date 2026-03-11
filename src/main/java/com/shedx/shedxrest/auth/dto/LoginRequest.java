package com.shedx.shedxrest.auth.dto;

public record LoginRequest(
    String email,
    String password,
    String CompanyKey
){}
