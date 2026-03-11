package com.shedx.shedxrest.auth.company;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Companys")
public class CompanyEntity {

    @Id
    @GeneratedValue
    Long id;

    @Column(unique=true)
    private String publicKey;
    private String name;

    private String companyUrl;

    public Long getId(){
        return id;
    }
    public String getUrl(){
        return companyUrl;
    }
    
}
