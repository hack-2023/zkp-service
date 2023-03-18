package com.hack2023.zkp_service.adapter.inbound;

import com.hack2023.zkp_service.adapter.inbound.register.RegisterEndpoint;
import com.hack2023.zkp_service.domain.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InboundAdapterConfig {
    @Bean
    public RegisterEndpoint registerEndpoint(UserService userService) {
        return new RegisterEndpoint(userService);
    }
}
