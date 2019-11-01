package com.px.tool.domain.request;

import com.px.tool.domain.RequestStatus;
import com.px.tool.domain.kiemhong.KiemHong;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KiemHongDashBoard extends DashBoard {
    public static KiemHongDashBoard fromEntity(KiemHong kiemHong) {
        KiemHongDashBoard kiemHongDashBoard = new KiemHongDashBoard();
        kiemHongDashBoard.setKey(String.valueOf(kiemHong.getKhId()));
        kiemHongDashBoard.setNoiDung(kiemHong.getPhanXuong());
        kiemHongDashBoard.setTrangThaiRequest(RequestStatus.DANG_CHO_DUYET);
        return kiemHongDashBoard;
    }
}
