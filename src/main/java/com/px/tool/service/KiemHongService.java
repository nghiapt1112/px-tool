package com.px.tool.service;

import com.px.tool.model.KiemHong;
import com.px.tool.model.response.KiemHongResponse;

import java.util.List;

public interface KiemHongService {
    KiemHongResponse findThongTinKiemHong(Long id);
    List<KiemHongResponse> findThongTinKiemHongCuaPhongBan(Long userId);

    /**
     * Buoc dau tien tao yeu cau kiem hong
     * @param kiemHong
     */
    KiemHong taoYeuCauKiemHong(KiemHong kiemHong);
}
