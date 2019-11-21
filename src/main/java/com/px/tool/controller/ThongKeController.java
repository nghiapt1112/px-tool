package com.px.tool.controller;

import com.px.tool.domain.request.ThongKePayload;
import com.px.tool.domain.request.service.RequestService;
import com.px.tool.infrastructure.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/thongke")
public class ThongKeController extends BaseController {

    @Autowired
    private RequestService requestService;

    @GetMapping
    private ThongKePayload getDataChoThongKe(HttpServletRequest httpServletRequest) {
        Long userId = extractUserInfo(httpServletRequest);
        return requestService.collectDataThongKe(userId);
    }
}
