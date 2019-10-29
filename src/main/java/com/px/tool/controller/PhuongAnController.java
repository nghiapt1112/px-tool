package com.px.tool.controller;

import com.px.tool.domain.RequestStatus;
import com.px.tool.domain.RequestType;
import com.px.tool.domain.phuongan.PhuongAnPayload;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.service.RequestService;
import com.px.tool.infrastructure.BaseController;
import com.px.tool.domain.phuongan.PhuongAn;
import com.px.tool.domain.phuongan.service.PhuongAnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pa")
public class PhuongAnController extends BaseController {
    @Autowired
    private PhuongAnService phuongAnService;

    @Autowired
    private RequestService requestService;

    @GetMapping("/{id}")
    public PhuongAn getPhuongAnDetail(@PathVariable Long id) {
        return phuongAnService.findById(id);
    }

    @GetMapping
    public List<PhuongAn> getPhuongAnTheoPhongBan(SecurityContextHolderAwareRequestWrapper httpServletRequest, @PathVariable Long id) {
        Long userId = extractUserInfo(httpServletRequest);
        return this.phuongAnService.findByPhongBan(userId);
    }

    @PostMapping
    public PhuongAn createPhuongAn(@RequestBody PhuongAnPayload phuongAnPayload) {
        logger.info("Tao Phuong An, \ndata: {}", phuongAnPayload);
        PhuongAn phuongAn = phuongAnPayload.toEntity();
        Request request = this.requestService.findById(phuongAnPayload.getRequestId());
        phuongAn.setRequest(request);
        request.setStatus(RequestType.CONG_NHAN_THANH_PHAM);
        return this.phuongAnService.createPhuongAn(phuongAn);
    }

    @PutMapping
    public PhuongAn capNhatPhuongAn(@RequestBody PhuongAnPayload phuongAnPayload) {
        logger.info("Cap nhat phuong an, \ndata: {}", phuongAnPayload);
        PhuongAn phuongAn = phuongAnPayload.toEntity();
        Request request = this.requestService.findById(phuongAnPayload.getRequestId());
        phuongAn.setRequest(request);
        return this.phuongAnService.createPhuongAn(phuongAn);
    }
}
