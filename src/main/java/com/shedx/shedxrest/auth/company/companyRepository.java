package com.shedx.shedxrest.auth.company;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface companyRepository extends JpaRepository<CompanyEntity, Long>{

    Optional<CompanyEntity> findByPublicKey(String companyKey);
        
    
}
