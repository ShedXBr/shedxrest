package com.shedx.shedxrest.auth.dto;

public record ProDepreRequest (
    String AdmEmail,
    String AdmPass,
    String UserEmail
){}
