package com.shedx.shedxrest.auth;


import java.time.LocalDateTime;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shedx.shedxrest.PasswordReset.PResetRepository;
import com.shedx.shedxrest.PasswordReset.PResetToken;
import com.shedx.shedxrest.auth.company.CompanyEntity;
import com.shedx.shedxrest.auth.company.companyRepository;
import com.shedx.shedxrest.auth.dto.LoginRequest;
import com.shedx.shedxrest.auth.dto.RegisterRequest;
import com.shedx.shedxrest.auth.dto.ResetPassRequest;
import com.shedx.shedxrest.auth.dto.UserAlredyExists;
import com.shedx.shedxrest.security.JwtService;

@Service
public class AuthService {
    private final UserRepository repo;
    private final BCryptPasswordEncoder encoder;
    private final JwtService jwtService;
    private final companyRepository Crepo;
    private final PResetRepository passRepo;

    public AuthService(
        UserRepository repo,
        BCryptPasswordEncoder encoder,
        JwtService jwtService,
        companyRepository Crepo,
        PResetRepository passRepo
    ){
        this.repo = repo;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.Crepo = Crepo;
        this.passRepo = passRepo;
    }

    public void register(RegisterRequest request, String companyKey){
        CompanyEntity company = Crepo
        .findByPublicKey(companyKey)
        .orElseThrow(() -> new BadCredentialsException("Invalid company"));
        if(repo.existsByEmailAndCompanyId(request.email(), company.getId())){
                throw new UserAlredyExists("User alredy Exists");
        }
        UserEntity user = new UserEntity();
        user.setEmail(request.email());
        user.setPassword(encoder.encode(request.password()));
        user.setCompanyId(company.getId());
        repo.save(user);
    }

    public String login(LoginRequest request, String companyKey){
        CompanyEntity company = Crepo.findByPublicKey(companyKey)
        .orElseThrow(()->new BadCredentialsException("Invalid Company"));
        UserEntity user = repo.findByEmailAndCompanyId(request.email(), company.getId())
            .orElseThrow(() -> new BadCredentialsException("Invalid Credentials"));
        if(!encoder.matches(request.password(), user.getPassword())){
            throw new BadCredentialsException("Invalid Credentials");
        }

        return jwtService.generateToken(user.getId(), company.getId());
    }
    @Transactional
    public void resetPassword(ResetPassRequest request, String companyKey){
        CompanyEntity company = Crepo.findByPublicKey(companyKey)
            .orElseThrow(() -> new BadCredentialsException("Invalid Company"));
        PResetToken resetToken = passRepo.findByToken(request.token())
        .orElseThrow(() -> new BadCredentialsException("Invalid Token"));
        if(resetToken.getExp().isBefore(LocalDateTime.now())){
            throw new BadCredentialsException("Token expired");
        }
        UserEntity user = resetToken.getUser();

        if(!user.getCompanyId().equals(company.getId())){
            throw new BadCredentialsException("Invalid Token");
        }

        user.setPassword(encoder.encode(request.newPassword()));
        repo.save(user);
        passRepo.delete(resetToken);
    }
}
// execute register, validate login, and generate the