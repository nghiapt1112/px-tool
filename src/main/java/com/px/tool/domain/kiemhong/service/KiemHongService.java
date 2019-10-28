package com.px.tool.domain.kiemhong.service;

import com.px.tool.domain.kiemhong.KiemHong;
import com.px.tool.domain.kiemhong.KiemHongPayLoad;

import java.util.List;

public interface KiemHongService {
    KiemHongPayLoad findThongTinKiemHong(Long id);
    List<KiemHongPayLoad> findThongTinKiemHongCuaPhongBan(Long userId);

    /**
     * Buoc dau tien tao yeu cau kiem hong
     * @param userId
     * @param kiemHong
     * @return
     */
    KiemHongPayLoad taoYeuCauKiemHong(Long userId, KiemHong kiemHong);

    KiemHongPayLoad capNhatKiemHong(Long userId, Long requestId, KiemHong kiemHong);
}
