package com.px.tool.controller;

import com.px.tool.infrastructure.BaseController;
import com.px.tool.domain.request.DashBoardPayload;
import com.px.tool.domain.request.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/dashboard")
public class DashBoardController extends BaseController {

    @Autowired
    private RequestService requestService;

    @GetMapping("/sender")
    public DashBoardPayload dashBoardChoNguoiGui(SecurityContextHolderAwareRequestWrapper httpServletRequest) {
        Long userId = extractUserInfo(httpServletRequest);
        return requestService.timByNguoiGui(Arrays.asList(userId));
    }

    @GetMapping("/receiver")
    public DashBoardPayload dashBoardChoNguoiNhan(SecurityContextHolderAwareRequestWrapper httpServletRequest) {
        Long userId = extractUserInfo(httpServletRequest);
        return requestService.timByNguoiNhan(Arrays.asList(userId));
    }
}
