package com.px.tool.controller;

import com.px.tool.domain.user.service.UserService;
import com.px.tool.infrastructure.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/req")
public class RequestController extends BaseController {
    @Autowired
    private UserService userService;

    @GetMapping
    public void getListNoiNhan(HttpServletRequest httpServletRequest) {

    }

}
