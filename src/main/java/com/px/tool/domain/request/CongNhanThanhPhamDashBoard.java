package com.px.tool.domain.request;

import com.px.tool.domain.RequestStatus;
import com.px.tool.domain.cntp.CongNhanThanhPham;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CongNhanThanhPhamDashBoard extends DashBoard {
    public static CongNhanThanhPhamDashBoard fromEntity(CongNhanThanhPham congNhanThanhPham) {
        CongNhanThanhPhamDashBoard congNhanThanhPhamDashBoard = new CongNhanThanhPhamDashBoard();
        congNhanThanhPhamDashBoard.setKey(String.valueOf(congNhanThanhPham.getTpId()));
        congNhanThanhPhamDashBoard.setNoiDung(congNhanThanhPham.getNoiDung());
        congNhanThanhPhamDashBoard.setTrangThaiRequest(RequestStatus.DANG_CHO_DUYET);
        return congNhanThanhPhamDashBoard;
    }
}
