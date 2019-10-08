package com.px.tool.controller;

import com.px.tool.model.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sec")
public class AuthController {

    @PostMapping("/login")
    public String login(LoginRequest loginRequest) {
        return loginRequest.toString();
    }
}
