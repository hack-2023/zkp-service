package com.hack2023.zkp_service.adapter.inbound;

import com.hack2023.zkp_service.adapter.inbound.challenge.ChallengeEndpoint;
import com.hack2023.zkp_service.adapter.inbound.login.LoginEndpoint;
import com.hack2023.zkp_service.adapter.inbound.register.UserEndpoint;
import com.hack2023.zkp_service.domain.UserService;
import com.hack2023.zkp_service.domain.login.ChallengeService;
import com.hack2023.zkp_service.domain.login.LoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InboundAdapterConfig {
    @Bean
    public UserEndpoint registerEndpoint(UserService userService) {
        return new UserEndpoint(userService);
    }

    @Bean
    public LoginEndpoint loginEndpoint(LoginService loginService) {return new LoginEndpoint(loginService);}

    @Bean
    public ChallengeEndpoint challengeEndpoint(ChallengeService challengeService) {return new ChallengeEndpoint(challengeService);}
}
