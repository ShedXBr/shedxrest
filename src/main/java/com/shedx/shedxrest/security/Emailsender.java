package com.shedx.shedxrest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.shedx.shedxrest.auth.UserEntity;
import com.shedx.shedxrest.auth.company.CompanyEntity;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class Emailsender {
    
    @Autowired
    private JavaMailSender mailSender;

    public void sendResetEmail(UserEntity user, String token, CompanyEntity company) throws MessagingException{
        String frontendUrl = company.getUrl();

        String link = frontendUrl + "/reset-password?token=" + token;

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        
        helper.setFrom("shedx99@gmail.com");
        helper.setTo(user.getEmail());
        helper.setSubject("Redefinição de Senha");
        String html = """
        <div style="background:#150e2a; padding:40px; text-align:center; font-family:Arial, sans-serif; border-radius:8px; max-width:500px; margin:0 auto;">
    <h2 style="color:#ffffff; margin-bottom:20px;">Redefinição de senha</h2>
    <p style="color:#dcd6f7; font-size:16px; margin-bottom:30px;">Clique no botão abaixo para redefinir sua senha:</p>
    <a href="%s" 
       style="display:inline-block; padding:12px 25px; background:#3f2970; color:#ffffff; text-decoration:none; font-weight:bold; border-radius:5px;">
       Redefinir senha
    </a>
    </div>
        """.formatted(link);
        helper.setText(html, true);
        mailSender.send(message);
    }
}
