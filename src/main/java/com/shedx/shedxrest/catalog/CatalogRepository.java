package com.shedx.shedxrest.catalog;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
public interface CatalogRepository extends JpaRepository<Catalog, Long>{

    List<Catalog> findByCompanyId(Long companyId);

    Optional<Catalog> findByNameAndCompanyId(String name, Long companyId);
    
}
//do the operations