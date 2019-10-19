package com.px.tool.service;

import com.px.tool.model.KiemHong;

public interface KiemHongService {
    KiemHong getThongTinKiemHong();

    /**
     * Buoc dau tien tao yeu cau kiem hong
     * @param kiemHong
     */
    KiemHong taoYeuCauKiemHong(KiemHong kiemHong);
}
