package com.shedx.shedxrest.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    private final JwtService jwtService;
    private final BCryptPasswordEncoder encoder;
    public JwtAuthenticationFilter(JwtService jwtService, BCryptPasswordEncoder encoder){
        this.jwtService = jwtService;
        this.encoder = encoder;
    }
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException{
        String path = request.getServletPath();

    if (path.startsWith("/auth/") || path.startsWith("/health")) {
        filterChain.doFilter(request, response);
        return;
    }
        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);
        Long userId = jwtService.extractUserId(token);
        Long companyId = jwtService.extractCompanyId(token);
        TenantContext.setCompanyId(companyId);
        var authentication = new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
