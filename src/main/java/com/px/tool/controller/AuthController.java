package com.px.tool.controller;

import com.px.tool.model.request.LoginRequest;
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
