package com.shedx.shedxrest.PasswordReset;

import java.time.LocalDateTime;

import com.shedx.shedxrest.auth.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class PResetToken {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String token;
    public void setToken(String token){
        this.token = token;
    }
    @OneToOne
    private UserEntity user;
    public void setUser(UserEntity user){
        this.user = user;
    }
    public UserEntity getUser(){
        return user;
    }
    private LocalDateTime expiration;
    public void setExp(LocalDateTime expiration){
        this.expiration = expiration;
    }
    public LocalDateTime getExp(){
        return expiration;
    }

}
