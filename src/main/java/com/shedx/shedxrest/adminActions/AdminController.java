package com.shedx.shedxrest.adminActions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shedx.shedxrest.auth.dto.ErrorReponse;
import com.shedx.shedxrest.auth.dto.ProDepreRequest;


@RestController
@RequestMapping("/company")
public class AdminController {
    private final AdminService service;
    public AdminController(AdminService service){
        this.service = service;
    } 
    @PostMapping("/promote")
    public ResponseEntity<?> promote(
        @RequestHeader("CompanyKey") String CompanyKey,
        @RequestBody ProDepreRequest request
    ){
        try {
            service.Promote(request, CompanyKey);
        return ResponseEntity.ok("Role Changed");
        }catch(BadCredentialsException e){
            return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorReponse(e.getMessage()));
        }
        
    }
    @PostMapping("/demote")
    public ResponseEntity<?> demote(
        @RequestHeader("CompanyKey") String CompanyKey,
        @RequestBody ProDepreRequest request
    ){
        try {
            service.Demote(request, CompanyKey);
        return ResponseEntity.ok("Role Changed");
        }catch(BadCredentialsException e){
            return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorReponse(e.getMessage()));
        }
        
    }
}
