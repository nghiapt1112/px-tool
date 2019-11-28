package com.px.tool.controller;

import com.px.tool.domain.request.payload.PageThongKePayload;
import com.px.tool.domain.request.payload.ThongKeRequest;
import com.px.tool.domain.request.service.RequestService;
import com.px.tool.infrastructure.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/thongke")
public class ThongKeController extends BaseController {

    @Autowired
    private RequestService requestService;

    @GetMapping
    private PageThongKePayload getDataChoThongKe(HttpServletRequest httpServletRequest,
                                                 @RequestParam(required = false, defaultValue = "-1") Long spId,
                                                 @RequestParam(required = false, defaultValue = "1") Integer page,
                                                 @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        Long userId = extractUserInfo(httpServletRequest);
        ThongKeRequest thongKeRequest = new ThongKeRequest();
        thongKeRequest.setSanPham(spId);
        thongKeRequest.setPage(page);
        thongKeRequest.setSize(size);
        return requestService.collectDataThongKe(thongKeRequest);
    }
}
