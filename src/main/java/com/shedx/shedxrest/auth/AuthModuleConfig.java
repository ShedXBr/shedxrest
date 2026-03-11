package com.shedx.shedxrest.auth;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
    name = "shedx.features.auth",
    havingValue = "true"
)
public class AuthModuleConfig {
    
}
//as this is like a rest api for all the companies, we need to control 
// if the company in the moment needs a auth or not, this can be configured in application.yml

