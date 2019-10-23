package com.px.tool.service;

import com.px.tool.model.KiemHong;
import com.px.tool.model.response.KiemHongResponse;

public interface KiemHongService {
    KiemHongResponse getThongTinKiemHong(Long id);
    KiemHongResponse getThongTinKiemHongCuaPhongBan(Long userId);

    /**
     * Buoc dau tien tao yeu cau kiem hong
     * @param kiemHong
     */
    KiemHong taoYeuCauKiemHong(KiemHong kiemHong);
}
