package com.shedx.shedxrest.auth;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(unique = true)
    private String email;
    private String password;
    
    private Long companyId;
    public Long getId(){ return id;}
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public Long getCompanyId(){
        return companyId;
    }

    public void setEmail(String email){ this.email = email;}
    public void setPassword(String password){this.password = password;}
    public void setCompanyId(Long companyId){this.companyId = companyId;}
}
