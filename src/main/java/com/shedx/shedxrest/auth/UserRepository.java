package com.shedx.shedxrest.auth;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
    Optional<UserEntity> findByEmailAndCompanyId(String email, Long companyId);
    List<UserEntity> findByCompanyId(Long companyId);
    boolean existsByEmailAndCompanyId(String email, Long companyId);
}
