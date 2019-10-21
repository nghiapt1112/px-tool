package com.px.tool.controller;

import com.px.tool.model.KiemHong;
import com.px.tool.model.KiemHongDetail;
import com.px.tool.model.PhongBan;
import com.px.tool.repository.PhongBanRepository;
import com.px.tool.service.KiemHongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/px")
public class PXController {
    @Autowired
    private KiemHongService kiemHongService;

    @Autowired
    private PhongBanRepository phongBanRepository;

    @GetMapping("/thongTinKiemHong")
    public KiemHong thongTinKiemHong() {
        KiemHong kiemHong = new KiemHong();
        Set<KiemHongDetail> kiemHongDetails = new HashSet<>();
        kiemHong.setKiemHongDetails(kiemHongDetails);

        return kiemHong;
    }

    @PostMapping("/taoKiemHong")
    public KiemHong taoKiemHong(@RequestBody KiemHong kiemHong) {
        Set<KiemHongDetail> kiemHongDetails = new HashSet<>();
        kiemHong.setKiemHongDetails(kiemHongDetails);

        return kiemHongService.taoYeuCauKiemHong(kiemHong);
    }

    @GetMapping
    public List<PhongBan> findPhongBans() {
        return phongBanRepository.findAll();
    }


}
