package com.px.tool.controller;

import com.px.tool.domain.RequestType;
import com.px.tool.domain.dathang.PhieuDatHangPayload;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.service.RequestService;
import com.px.tool.infrastructure.BaseController;
import com.px.tool.domain.dathang.PhieuDatHang;
import com.px.tool.domain.dathang.service.PhieuDatHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pdh")
public class PhieuDatHangController extends BaseController {
    @Autowired
    private PhieuDatHangService phieuDatHangService;

    @Autowired
    private RequestService requestService;

    @GetMapping("/{id}")
    public PhieuDatHang getPhieuDatHangDetail(@PathVariable Long id) {
        return this.phieuDatHangService.findById(id);
    }

    @GetMapping
    public List<PhieuDatHang> getPhieuDatHangTheoPhongBan(SecurityContextHolderAwareRequestWrapper httpServletRequest) {
        Long userId = extractUserInfo(httpServletRequest);
        return this.phieuDatHangService.findByPhongBan(userId);

    }

    @PostMapping
    public void taoPhieuDatHang(@RequestBody PhieuDatHangPayload phieuDatHangPayload) {
        logger.info("Tao Phieui dat hang, \ndata: {}", phieuDatHangPayload);
        PhieuDatHang phieuDatHang = phieuDatHangPayload.toEntity();
        Request request = this.requestService.findById(phieuDatHangPayload.getRequestId());
        phieuDatHang.setRequest(request);
        request.setStatus(RequestType.PHUONG_AN);
        this.phieuDatHangService.create(phieuDatHang);
    }

    @PutMapping
    public PhieuDatHang capNhatPhieuDatHang(@RequestBody PhieuDatHangPayload phieuDatHangPayload) {
        logger.info("update phieu dat hang, \ndata: {}", phieuDatHangPayload);
        PhieuDatHang phieuDatHang = phieuDatHangPayload.toEntity();
        Request request = this.requestService.findById(phieuDatHangPayload.getRequestId());
        phieuDatHang.setRequest(request);
        return this.phieuDatHangService.save(phieuDatHang);
    }

    @DeleteMapping
    public void xoaPhieuDatHang() {
        
    }
}
