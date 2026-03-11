package com.shedx.shedxrest.Users;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shedx.shedxrest.auth.UserEntity;
import com.shedx.shedxrest.auth.UserRepository;
import com.shedx.shedxrest.security.TenantContext;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/users")
public class UsersController {
    private final UserRepository repo;
    public UsersController(UserRepository repo){
        this.repo=repo;
    }
    @GetMapping
    public List<UserEntity> list(){
        long companyId = TenantContext.getCompanyId();

        return repo.findByCompanyId(companyId);
    }
    
}
