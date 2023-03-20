package com.hack2023.zkp_service.adapter.inbound.login;

import com.hack2023.zkp_service.domain.login.LoginRequest;
import com.hack2023.zkp_service.domain.login.LoginResponse;
import com.hack2023.zkp_service.domain.login.LoginService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.text.ParseException;

@RestController
public class LoginEndpoint {
    private LoginService loginService;

    public LoginEndpoint(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) throws ParseException, JOSEException {
        return loginService.login(request);
    }

    @GetMapping("/login")
    public void login(@RequestParam String token, HttpServletResponse response) throws IOException {
        response.setHeader("Set-Cookie", "zkp-cookie="+token+"; Path=/; Max-Age=86400;");
        response.sendRedirect("/welcome");
    }

    @GetMapping("/welcome")
    public ModelAndView welcome(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("welcome.html");
        return modelAndView;
    }
}
