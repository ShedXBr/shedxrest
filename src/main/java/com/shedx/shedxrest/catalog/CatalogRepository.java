package com.shedx.shedxrest.catalog;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
public interface CatalogRepository extends JpaRepository<Catalog, Long>{

    List<Catalog> findByCompanyId(Long companyId);
    
}
//do the operations