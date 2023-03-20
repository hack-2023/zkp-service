package com.hack2023.zkp_service.adapter.inbound.login;

import com.hack2023.zkp_service.domain.login.LoginRequest;
import com.hack2023.zkp_service.domain.login.LoginResponse;
import com.hack2023.zkp_service.domain.login.LoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginEndpoint {
    private LoginService loginService;

    public LoginEndpoint(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){
        return loginService.login(request);
    }
}
