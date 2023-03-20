package com.hack2023.zkp_service.adapter.inbound;

import com.hack2023.zkp_service.adapter.inbound.challenge.ChallengeEndpoint;
import com.hack2023.zkp_service.adapter.inbound.login.LoginEndpoint;
import com.hack2023.zkp_service.adapter.inbound.login.LoginFilter;
import com.hack2023.zkp_service.adapter.inbound.register.UserEndpoint;
import com.hack2023.zkp_service.domain.UserService;
import com.hack2023.zkp_service.domain.login.ChallengeService;
import com.hack2023.zkp_service.domain.login.LoginService;
import com.hack2023.zkp_service.domain.token.TokenService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
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

    @Bean
    public FilterRegistrationBean<LoginFilter> loginFilter(TokenService tokenService) {
        FilterRegistrationBean<LoginFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoginFilter(tokenService));
        registrationBean.addUrlPatterns("/welcome");
        registrationBean.setOrder(2);
        return registrationBean;
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {return new GlobalExceptionHandler();}
}
