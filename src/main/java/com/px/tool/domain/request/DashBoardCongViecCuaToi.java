package com.px.tool.domain.request;

import com.px.tool.domain.RequestStatus;
import com.px.tool.domain.RequestType;
import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashBoardCongViecCuaToi extends AbstractObject {
    private long requestId;
    private String noiDung;
    private RequestStatus status;
    private RequestType type;

    public static DashBoardCongViecCuaToi fromEntity(Request request) {
        DashBoardCongViecCuaToi dashBoardCongViecCuaToi = new DashBoardCongViecCuaToi();
        dashBoardCongViecCuaToi.requestId = request.getRequestId();
        dashBoardCongViecCuaToi.type = request.getType();
        if (dashBoardCongViecCuaToi.type == RequestType.KIEM_HONG) {
            dashBoardCongViecCuaToi.noiDung = request.getKiemHong().getPhanXuong();
        } else if (dashBoardCongViecCuaToi.type == RequestType.PHIEU_DAT_HANG) {
            dashBoardCongViecCuaToi.noiDung = request.getPhieuDatHang().getNoiDung();
        } else if (dashBoardCongViecCuaToi.type == RequestType.PHUONG_AN) {
            dashBoardCongViecCuaToi.noiDung = request.getPhuongAn().getNoiDung();
        } else if (dashBoardCongViecCuaToi.type == RequestType.CNTP) {
            dashBoardCongViecCuaToi.noiDung = request.getCongNhanThanhPham().getNoiDung();
        }
        return dashBoardCongViecCuaToi;
    }
}
