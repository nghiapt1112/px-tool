package com.px.tool.service.impl;

import com.px.tool.model.KiemHong;
import com.px.tool.repository.KiemHongRepository;
import com.px.tool.service.KiemHongService;
import org.springframework.beans.factory.annotation.Autowired;

public class KiemHongServiceImpl implements KiemHongService {
    @Autowired
    private KiemHongRepository kiemHongRepository;

    @Override
    public KiemHong getThongTinKiemHong() {
        return null;
    }

    @Override
    public KiemHong taoYeuCauKiemHong(KiemHong kiemHong) {
        return kiemHongRepository.save(kiemHong);
    }
}
