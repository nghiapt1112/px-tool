package com.px.tool.controller;

import com.px.tool.model.KiemHong;
import com.px.tool.model.KiemHongDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/px")
public class PXController {

    @GetMapping("/thongTinKiemHong")
    public void thongTinKiemHong() {
        KiemHong  kiemHong = new KiemHong();
        Set<KiemHongDetail> kiemHongDetails = new HashSet<>();
        kiemHong.setKiemHongDetails(kiemHongDetails);
    }
}
