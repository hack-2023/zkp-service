package com.hack2023.zkp_service.adapter.outbound;

import com.hack2023.zkp_service.adapter.outbound.user.UserRepository;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OutboundAdapterConfig {
    @Autowired
    DSLContext context;
    @Bean
    public UserRepository userRepository() {
        return new UserRepository(context);
    }
}
