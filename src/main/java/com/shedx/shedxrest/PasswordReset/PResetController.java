package com.shedx.shedxrest.PasswordReset;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shedx.shedxrest.auth.AuthService;
import com.shedx.shedxrest.auth.UserEntity;
import com.shedx.shedxrest.auth.UserRepository;
import com.shedx.shedxrest.auth.company.CompanyEntity;
import com.shedx.shedxrest.auth.company.companyRepository;
import com.shedx.shedxrest.auth.dto.PassRequest;
import com.shedx.shedxrest.auth.dto.ResetPassRequest;
import com.shedx.shedxrest.security.Emailsender;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/auth")
public class PResetController {
    private final companyRepository Crepo;
    private final UserRepository repo;
    private final PResetRepository passRepo;
    private final AuthService service;
    private final Emailsender emailsender;
    public PResetController(UserRepository repo, companyRepository Crepo, 
        PResetRepository passRepo,
    AuthService service,
Emailsender emailsender){
        this.repo = repo;
        this.Crepo = Crepo;
        this.passRepo = passRepo;
        this.service = service;
        this.emailsender = emailsender;
    }
    @Transactional
    @PostMapping("/resetrequest")
    public ResponseEntity<?> resetPass(@RequestBody PassRequest request, @RequestHeader("CompanyKey") String companyKey){
        CompanyEntity company = Crepo.findByPublicKey(companyKey)
        .orElseThrow(()->new BadCredentialsException("Invalid Company"));
        UserEntity user = repo.findByEmailAndCompanyId(request.email(), company.getId())
        .orElseThrow(()->new BadCredentialsException("Invalid User"));
        String token = UUID.randomUUID().toString();
        PResetToken resetToken = passRepo.findByUser(user).orElse(new PResetToken());
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExp(LocalDateTime.now().plusMinutes(15));

        passRepo.save(resetToken);
        try {
            emailsender.sendResetEmail(user, token, company);
        } catch (MessagingException ex) {
          ex.printStackTrace();
        }
        
        return ResponseEntity.ok("Email de recuperação Enviado");
        
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
        @RequestBody ResetPassRequest request,
        @RequestHeader("CompanyKey") String companyKey
    ){
        service.resetPassword(request, companyKey);

        return ResponseEntity.ok("Senha Alterada com sucesso!");
    }
}
 