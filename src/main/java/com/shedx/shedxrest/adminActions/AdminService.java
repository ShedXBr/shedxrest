package com.shedx.shedxrest.adminActions;


import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shedx.shedxrest.auth.UserEntity;
import com.shedx.shedxrest.auth.UserRepository;
import com.shedx.shedxrest.auth.company.CompanyEntity;
import com.shedx.shedxrest.auth.company.companyRepository;
import com.shedx.shedxrest.auth.dto.ProDepreRequest;


@Service
public class AdminService {
    private final UserRepository Urepo;
    private final companyRepository Crepo;
    private final BCryptPasswordEncoder encoder;

    public AdminService(UserRepository Urepo, companyRepository Crepo, BCryptPasswordEncoder encoder){
        this.Urepo = Urepo;
        this.Crepo = Crepo;
        this.encoder = encoder;
    }
    @Transactional
    public void Promote(ProDepreRequest request, String CompanyKey){
        CompanyEntity Company = Crepo.findByPublicKey(CompanyKey)
        .orElseThrow(() -> new BadCredentialsException("Invalid company"));
        
        UserEntity userAdm = Urepo.findByEmailAndCompanyId(request.AdmEmail(), Company.getId())
        .orElseThrow(() -> new BadCredentialsException("User Doesn't Exist"));
        if(!encoder.matches(request.AdmPass(), userAdm.getPassword())){
            throw new BadCredentialsException("Invalid Credentials");
        }else{
            UserEntity userPro = Urepo.findByEmailAndCompanyId(request.UserEmail(), Company.getId())
            .orElseThrow(() -> new BadCredentialsException("User Doesn't Exist"));
            if(userAdm.getRole().equalsIgnoreCase("ROLE_ADMIN")){
                if(userAdm.getCompanyId().equals(userPro.getCompanyId())){
                    if(userPro.getRole().equalsIgnoreCase("ROLE_USER")){
                        userPro.setRole("ROLE_ADMIN");
                    }else{
                        throw new BadCredentialsException("User is Alredy Admin");
                    }
                }else{
                    throw new BadCredentialsException("Are not same Company");
                }
            }else{
                throw new BadCredentialsException("User is not Admin");
            }
        }
        
    }
    @Transactional
    public void Demote(ProDepreRequest request, String CompanyKey){
        CompanyEntity Company = Crepo.findByPublicKey(CompanyKey)
        .orElseThrow(() -> new BadCredentialsException("Invalid company"));
        long totalAdmins= Urepo.countByRoleAndCompanyId("ROLE_ADMIN", Company.getId());
        UserEntity userAdm = Urepo.findByEmailAndCompanyId(request.AdmEmail(), Company.getId())
        .orElseThrow(() -> new BadCredentialsException("User Doesn't Exist"));
        if(!encoder.matches(request.AdmPass(), userAdm.getPassword())){
            throw new BadCredentialsException("Invalid Credentials");
        }else{
            UserEntity userPro = Urepo.findByEmailAndCompanyId(request.UserEmail(), Company.getId())
            .orElseThrow(() -> new BadCredentialsException("User Doesn't Exist"));
            if(userAdm.getRole().equalsIgnoreCase("ROLE_ADMIN")){
                if(request.AdmEmail().equalsIgnoreCase(request.UserEmail())){
                    if(totalAdmins == 1){
                        throw new BadCredentialsException("Only you are Admin");
                    }else{
                        if(userAdm.getCompanyId().equals(userPro.getCompanyId())){
                            if(userPro.getRole().equalsIgnoreCase("ROLE_ADMIN")){
                                userPro.setRole("ROLE_USER");
                            }else{
                                throw new BadCredentialsException("User is Alredy User");
                            }
                        }else{
                            throw new BadCredentialsException("Are not same Company");
                        }
                    }
                }else{
                    if(userAdm.getCompanyId().equals(userPro.getCompanyId())){
                        if(userPro.getRole().equalsIgnoreCase("ROLE_ADMIN")){
                            userPro.setRole("ROLE_USER");
                        }else{
                            throw new BadCredentialsException("User is Alredy User");
                        }
                    }else{
                        throw new BadCredentialsException("Are not same Company");
                    }
                }
            }else{
                throw new BadCredentialsException("User is not Admin");
            }
        
    }
}
}
