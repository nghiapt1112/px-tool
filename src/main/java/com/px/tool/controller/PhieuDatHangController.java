package com.px.tool.controller;

import com.px.tool.infrastructure.BaseController;
import com.px.tool.model.PhieuDatHang;
import com.px.tool.model.response.PhieuDatHangPayload;
import com.px.tool.service.PhieuDatHangService;
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
    public void taoPhieuDatHang(@RequestBody PhieuDatHang phieuDatHang) {
//        PhieuDatHang phieuDatHang = new PhieuDatHang();
//        phieuDatHangPayload.toEntity(phieuDatHang);
        this.phieuDatHangService.create(phieuDatHang);
    }

    @PutMapping
    public PhieuDatHang capNhatPhieuDatHang(@RequestBody PhieuDatHang phieuDatHang) {
//        PhieuDatHang phieuDatHang = new PhieuDatHang();
//        phieuDatHangPayload.toEntity(phieuDatHang);
        return this.phieuDatHangService.save(phieuDatHang);
    }

    @DeleteMapping
    public void xoaPhieuDatHang() {
        
    }
}
