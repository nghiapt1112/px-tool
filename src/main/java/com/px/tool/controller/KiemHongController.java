package com.px.tool.controller;

import com.px.tool.infrastructure.BaseController;
import com.px.tool.model.KiemHong;
import com.px.tool.model.PhongBan;
import com.px.tool.model.response.KiemHongResponse;
import com.px.tool.repository.PhongBanRepository;
import com.px.tool.service.KiemHongService;
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
@RequestMapping("/px")
public class KiemHongController extends BaseController {
    @Autowired
    private KiemHongService kiemHongService;

    @Autowired
    private PhongBanRepository phongBanRepository;

    @GetMapping("/ttkh/{id}")
    public KiemHongResponse thongTinKiemHong(@PathVariable Long id) {
        return kiemHongService.findThongTinKiemHong(id);
    }

    @PostMapping("/tkh")
    public KiemHong taoKiemHong(SecurityContextHolderAwareRequestWrapper httpServletRequest, @RequestBody KiemHong kiemHong) {
        Long userId = extractUserInfo(httpServletRequest);
        kiemHong.setCreatedBy(userId);
        return kiemHongService.taoYeuCauKiemHong(kiemHong);
    }

    @PutMapping("/ukh")
    public KiemHong chinhSuaKiemHong(SecurityContextHolderAwareRequestWrapper httpServletRequest, @RequestBody KiemHong kiemHong) {
        Long userId = extractUserInfo(httpServletRequest);
        kiemHong.setCreatedBy(userId);
        return kiemHongService.taoYeuCauKiemHong(kiemHong);
    }

    @GetMapping("/pb")
    public List<PhongBan> findPhongBans() {
        return phongBanRepository.findAll();
    }


    /**
     * tu dong get tat ca thong tin kiem hong cua 1 phong ban
     */
    @GetMapping("/lkh")
    public List<KiemHongResponse> getListKiemHongTheoPhongBan(SecurityContextHolderAwareRequestWrapper httpServletRequest) {
        Long userId = extractUserInfo(httpServletRequest);
        return kiemHongService.findThongTinKiemHongCuaPhongBan(userId);
    }

}
