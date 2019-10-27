package com.px.tool.service;

import com.px.tool.model.KiemHong;
import com.px.tool.model.response.KiemHongPayLoad;

import java.util.List;

public interface KiemHongService {
    KiemHongPayLoad findThongTinKiemHong(Long id);
    List<KiemHongPayLoad> findThongTinKiemHongCuaPhongBan(Long userId);

    /**
     * Buoc dau tien tao yeu cau kiem hong
     * @param kiemHong
     */
    KiemHong taoYeuCauKiemHong(KiemHong kiemHong);
}
