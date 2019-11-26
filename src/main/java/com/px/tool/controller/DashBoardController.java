package com.px.tool.controller;

import com.px.tool.domain.request.payload.DashBoardCongViecCuaToi;
import com.px.tool.domain.request.payload.PageRequest;
import com.px.tool.domain.request.service.RequestService;
import com.px.tool.infrastructure.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashBoardController extends BaseController {

    @Autowired
    private RequestService requestService;

    @GetMapping("/receiver")
    public List<DashBoardCongViecCuaToi> getListcongViecCuaToi(SecurityContextHolderAwareRequestWrapper httpServletRequest,
                                                               @RequestParam(required = false, defaultValue = "1") Integer page,
                                                               @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        Long userId = extractUserInfo(httpServletRequest);
        return requestService.timByNguoiNhan(userId, new PageRequest(page, size));
    }
}
