package com.shedx.shedxrest.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shedx.shedxrest.auth.dto.AuthResponse;
import com.shedx.shedxrest.auth.dto.ErrorReponse;
import com.shedx.shedxrest.auth.dto.LoginRequest;
import com.shedx.shedxrest.auth.dto.RegisterRequest;
import com.shedx.shedxrest.auth.dto.UserAlredyExists;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService service;
    public AuthController(AuthService service){this.service = service;}
    @PostMapping("/login")
    public ResponseEntity<?> login(
        @RequestHeader("CompanyKey") String companyKey,
        @RequestBody LoginRequest request){
        try{
            String token = service.login(request, companyKey);
            return ResponseEntity.ok(new AuthResponse(token));
        }catch(BadCredentialsException e){
            return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(new ErrorReponse("Invalid Credentials!"));
        }
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request,
        @RequestHeader("CompanyKey") String companyKey
    ) {
        try {
            service.register(request, companyKey);
            return ResponseEntity.ok("User registered");
        } catch (BadCredentialsException e) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorReponse("Invalid Company!"));
        } catch(UserAlredyExists e){
            return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(new ErrorReponse(e.getMessage()));
        }
    }
    
}
//Endpoints to register and login
