package com.shedx.shedxrest.auth.dto;

public record ResetPassRequest(
    String token,
    String newPassword
){}
