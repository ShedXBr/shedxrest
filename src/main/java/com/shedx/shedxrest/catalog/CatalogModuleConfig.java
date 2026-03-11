package com.shedx.shedxrest.catalog;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(
    name="shedx.features.catalog",
    havingValue = "true"
)
public class CatalogModuleConfig {
    
}
//as this is like a rest api for all the companies, we need to control 
// if the company in the moment needs a catalog or not, this can be configured in application.yml
