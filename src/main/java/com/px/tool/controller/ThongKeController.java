package com.px.tool.controller;

import com.px.tool.domain.request.payload.ThongKePageRequest;
import com.px.tool.domain.request.payload.ThongKePageResponse;
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
    private ThongKePageResponse getDataChoThongKe(HttpServletRequest httpServletRequest,
                                                  @RequestParam(required = false, defaultValue = "-1") Long spId,
                                                  @RequestParam(required = false, defaultValue = "-1") Long pxId,
                                                  @RequestParam(required = false, defaultValue = "0") Long fromDate,
                                                  @RequestParam(required = false, defaultValue = "-1") Long toDate,
                                                  @RequestParam(required = false, defaultValue = "1") Integer page,
                                                  @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        Long userId = extractUserInfo(httpServletRequest);
        ThongKePageRequest thongKeRequest = new ThongKePageRequest();
        thongKeRequest.setSanPham(spId);
        thongKeRequest.setFromDate(fromDate);
        thongKeRequest.setToDate(toDate);
        thongKeRequest.setPage(page);
        thongKeRequest.setSize(size);
        return requestService.collectDataThongKe(thongKeRequest);
    }
}
