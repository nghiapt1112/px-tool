package com.px.tool.controller;

import com.px.tool.model.CongNhanThanhPham;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("cntp")
public class CongNhanThanhPhamController {
    @GetMapping("")
    public CongNhanThanhPham timCongNhanThanhPham() {
        return null;
    }

    @GetMapping("")
    public List<CongNhanThanhPham> getCongNhanThanhPhamTheoPhongBan(SecurityContextHolderAwareRequestWrapper httpServletRequest) {
        return Collections.emptyList();
    }

    @PostMapping("")
    public CongNhanThanhPham taoCongNhanThanhPham() {
        return null;
    }


    @PutMapping
    public CongNhanThanhPham updateCongNhanThanhPham() {
        return null;
    }


    @DeleteMapping
    public long deleteCongNhanThanhPham() {
        return 1L;
    }

}
