package com.hack2023.zkp_service.adapter.inbound.login;

import com.hack2023.zkp_service.domain.token.TokenService;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class LoginFilter extends OncePerRequestFilter {
    private static final Logger LOG = LoggerFactory.getLogger(LoginFilter.class);
    private TokenService tokenService;

    public LoginFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        LOG.info("Incoming Request  {} : {}", request.getMethod(), request.getRequestURI());
        if(isValidZkpCookie(request)) {
            filterChain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "The zkp-cookie not found or invalid. Please solve the zpk challenge");
        }
        LOG.info("Incoming Response :{}", response.getContentType());
    }

    private boolean isValidZkpCookie(HttpServletRequest request) {
        if(request.getCookies() == null || request.getCookies().length == 0) {
            return false;
        }
        Optional<String> zkpCookie = Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("zkp-cookie"))
                .findFirst()
                .map(Cookie::getValue);
        if(! zkpCookie.isPresent()) {
            return false;
        }
        LOG.info("zkp-cookie  :{}", zkpCookie.get());
        JWTClaimsSet claimsSet = tokenService.consumeJweToken(zkpCookie.get());
        if(claimsSet.getClaim("zkp-challenge").equals("success")) {
            return true;
        } else {
            return false;
        }
    }
}
