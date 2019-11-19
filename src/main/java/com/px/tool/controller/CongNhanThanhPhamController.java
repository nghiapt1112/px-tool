package com.px.tool.controller;

import com.px.tool.domain.cntp.CongNhanThanhPham;
import com.px.tool.domain.cntp.CongNhanThanhPhamPayload;
import com.px.tool.domain.cntp.service.CongNhanThanhPhamService;
import com.px.tool.infrastructure.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cntp")
public class CongNhanThanhPhamController extends BaseController {

    @Autowired
    private CongNhanThanhPhamService congNhanThanhPhamService;

    @GetMapping("/{id}")
    public CongNhanThanhPhamPayload timCongNhanThanhPham(SecurityContextHolderAwareRequestWrapper httpServletRequest, @PathVariable Long id) {
        return this.congNhanThanhPhamService.timCongNhanThanhPham(extractUserInfo(httpServletRequest), id);
    }

    @GetMapping
    public List<CongNhanThanhPham> getCongNhanThanhPhamTheoPhongBan(SecurityContextHolderAwareRequestWrapper httpServletRequest) {
        Long userId = extractUserInfo(httpServletRequest);
        return this.congNhanThanhPhamService.timCongNhanThanhPhamTheoPhongBan(userId);
    }

    @PostMapping
    public CongNhanThanhPham taoCongNhanThanhPham(@RequestBody CongNhanThanhPhamPayload congNhanThanhPhamPayload) {
        return congNhanThanhPhamService.taoCongNhanThanhPham(congNhanThanhPhamPayload);
    }

    @DeleteMapping
    public long deleteCongNhanThanhPham() {
        return 1L;
    }

}
