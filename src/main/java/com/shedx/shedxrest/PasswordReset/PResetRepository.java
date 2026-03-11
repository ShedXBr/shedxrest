package com.shedx.shedxrest.PasswordReset;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shedx.shedxrest.auth.UserEntity;



public interface PResetRepository extends JpaRepository<PResetToken, Long>{
    
    
    

    Optional<PResetToken> findByToken(String token);

    Optional<PResetToken> findByUser(UserEntity user);
}
