package com.shedx.shedxrest.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
    private String role;
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
    public String getRole(){
        return role;
    }

    public void setEmail(String email){ this.email = email;}
    public void setPassword(String password){this.password = password;}
    public void setCompanyId(Long companyId){this.companyId = companyId;}
    public void setRole(String role){this.role = role;}
}
