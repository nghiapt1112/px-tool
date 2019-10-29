package com.px.tool.domain.request;

import com.px.tool.domain.RequestStatus;
import com.px.tool.domain.RequestType;
import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Getter
@Setter
public class DashBoardCongViecCuaToi extends AbstractObject {
    private String ma;
    private long requestId;
    private String noiDung;
    private RequestStatus status;
    private RequestType type;

    public static DashBoardCongViecCuaToi fromEntity(Request request) {
        DashBoardCongViecCuaToi dashBoardCongViecCuaToi = new DashBoardCongViecCuaToi();
        dashBoardCongViecCuaToi.ma = "Key-" + request.getRequestId();
        dashBoardCongViecCuaToi.requestId = request.getRequestId();
        dashBoardCongViecCuaToi.type = request.getType();
        if (dashBoardCongViecCuaToi.type == RequestType.KIEM_HONG) {
            dashBoardCongViecCuaToi.noiDung = request.getKiemHong().getPhanXuong();
        } else if (dashBoardCongViecCuaToi.type == RequestType.DAT_HANG) {
            dashBoardCongViecCuaToi.noiDung = request.getPhieuDatHang().getNoiDung();
        } else if (dashBoardCongViecCuaToi.type == RequestType.PHUONG_AN) {
            dashBoardCongViecCuaToi.noiDung = request.getPhuongAn().getNoiDung();
        } else if (dashBoardCongViecCuaToi.type == RequestType.CONG_NHAN_THANH_PHAM) {
            dashBoardCongViecCuaToi.noiDung = request.getCongNhanThanhPham().getNoiDung();
        }
        dashBoardCongViecCuaToi.status = RequestStatus.DANG_CHO_DUYET;
        return dashBoardCongViecCuaToi;
    }
}
