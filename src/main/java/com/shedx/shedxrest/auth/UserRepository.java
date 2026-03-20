package com.shedx.shedxrest.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
    Optional<UserEntity> findByEmailAndCompanyId(String email, Long companyId);
    List<UserEntity> findByCompanyId(Long companyId);
    boolean existsByEmailAndCompanyId(String email, Long companyId);
    boolean existsByCompanyId(Long companyId);
    long countByRoleAndCompanyId(String Role, Long companyId);
}
