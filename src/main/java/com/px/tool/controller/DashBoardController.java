package com.px.tool.controller;

import com.px.tool.infrastructure.BaseController;
import com.px.tool.model.response.DashBoardPayload;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashBoardController extends BaseController {

    @GetMapping
    public DashBoardPayload getDashBoard(SecurityContextHolderAwareRequestWrapper httpServletRequest) {
        Long userId = extractUserInfo(httpServletRequest);
        return new DashBoardPayload();
    }
}
