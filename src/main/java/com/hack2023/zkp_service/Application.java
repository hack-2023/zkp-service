package com.hack2023.zkp_service;

import com.hack2023.zkp_service.adapter.inbound.InboundAdapterConfig;
import com.hack2023.zkp_service.adapter.outbound.OutboundAdapterConfig;
import com.hack2023.zkp_service.domain.DomainConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration
@EnableAutoConfiguration
@Import({InboundAdapterConfig.class, OutboundAdapterConfig.class, DomainConfig.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}