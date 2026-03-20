package com.shedx.shedxrest.catalog;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shedx.shedxrest.auth.UserEntity;
import com.shedx.shedxrest.auth.UserRepository;
import com.shedx.shedxrest.auth.company.CompanyEntity;
import com.shedx.shedxrest.auth.company.companyRepository;
import com.shedx.shedxrest.security.TenantContext;



@RestController
@RequestMapping("/catalogs")
public class CatalogController {
    private final CatalogRepository repo;
    private final companyRepository Crepo;
    private final UserRepository Urepo;
    public CatalogController(CatalogRepository repo, companyRepository Crepo, UserRepository Urepo){
        this.repo = repo;
        this.Crepo = Crepo;
        this.Urepo = Urepo;
        
    }
    @GetMapping
    public List<Catalog> list(@RequestHeader String CompanyKey) {
        CompanyEntity company = Crepo.findByPublicKey(CompanyKey)
        .orElseThrow(()-> new BadCredentialsException("Invalid Company"));
        return repo.findByCompanyId(company.getId());
    }
    @PostMapping("/register")
    public ResponseEntity<String> create(@RequestBody Catalog catalog, Authentication auth){
        CompanyEntity company = Crepo.findById(TenantContext.getCompanyId())
        .orElseThrow(()->new BadCredentialsException("Invalid Company"));
        UserEntity user;
        try {
            user = Urepo.findById(Long.valueOf(auth.getName()))
            .orElseThrow(()-> new BadCredentialsException("Invalid Auth"));
        } catch (NumberFormatException e) {
            throw new BadCredentialsException(e.getMessage());
        }
        if(user.getRole().equalsIgnoreCase("ROLE_ADMIN")){
            Catalog catalogEn = new Catalog();
            catalogEn.setName(catalog.getName());
            catalogEn.setPrice(catalog.getPrice());
            catalogEn.setDescProduct(catalog.getDescProduct());
            catalogEn.setCompanyId(company.getId());
            repo.save(catalogEn);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product registered");
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User Not Authorized");
        }
        
        
    }
    
}//Rest to create and list catalogs
