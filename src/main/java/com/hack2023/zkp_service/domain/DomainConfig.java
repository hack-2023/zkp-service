package com.hack2023.zkp_service.domain;

import com.hack2023.zkp_service.domain.login.ChallengeService;
import com.hack2023.zkp_service.domain.login.LoginService;
import com.hack2023.zkp_service.domain.token.TokenService;
import com.hack2023.zkp_service.port.UserRepositoryPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {
    @Value( "${ec_key}" )
    private String ecKey;

    @Value( "${login.challenge.minRounds}" )
    private int minChallengeRounds;

    @Value( "${login.challenge.maxRounds}" )
    private int maxChallengeRounds;
    @Bean
    public UserService userService(UserRepositoryPort userRepository) {
        return new UserService(userRepository);
    }

    @Bean
    public LoginService loginService(UserRepositoryPort userRepository, TokenService tokenService) {return new LoginService(userRepository, tokenService, minChallengeRounds, maxChallengeRounds);}

    @Bean
    public TokenService tokenService() {return new TokenService(ecKey);}

    @Bean
    public ChallengeService challengeService(UserRepositoryPort userRepository, TokenService tokenService) {return new ChallengeService(userRepository, tokenService);}
}