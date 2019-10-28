package com.px.tool.domain.request;

import com.px.tool.domain.dathang.PhieuDatHang;
import com.px.tool.domain.TrangThaiRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatHangDashBoard extends DashBoard {
    public static DatHangDashBoard fromEntity(PhieuDatHang phieuDatHang) {
        DatHangDashBoard datHangDashBoard = new DatHangDashBoard();
        datHangDashBoard.setKey(String.valueOf(phieuDatHang.getPdhId()));
        datHangDashBoard.setNoiDung(phieuDatHang.getNoiDung());
        datHangDashBoard.setTrangThaiRequest(TrangThaiRequest.DANG_CHO_DUYET);
        return datHangDashBoard;
    }
}
