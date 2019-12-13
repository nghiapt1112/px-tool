package com.px.tool.domain.request.payload;

import com.px.tool.domain.RequestType;
import com.px.tool.domain.cntp.CongNhanThanhPham;
import com.px.tool.domain.phuongan.PhuongAn;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.user.User;
import com.px.tool.infrastructure.model.payload.AbstractObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Getter
@Setter
public class DashBoardCongViecCuaToi extends AbstractObject {
    private String ma;
    private long requestId;
    private String noiDung;
    private String status;
    private RequestType type;

    public static DashBoardCongViecCuaToi fromEntity(Request request, Map<Long, User> userById) {
        DashBoardCongViecCuaToi dashBoardCongViecCuaToi = new DashBoardCongViecCuaToi();
        dashBoardCongViecCuaToi.ma = "Key-" + request.getRequestId();
        dashBoardCongViecCuaToi.requestId = request.getRequestId();
        dashBoardCongViecCuaToi.type = request.getType();

        dashBoardCongViecCuaToi.noiDung = "Gửi từ phân xưởng: " + getVal(userById, request.getKiemHong().getPhanXuong()) + "- Tổ sản xuất: " + getVal(userById, request.getKiemHong().getToSX());
        dashBoardCongViecCuaToi.status = "Vừa tạo";
        try {
            if (dashBoardCongViecCuaToi.type == RequestType.KIEM_HONG) {
                if (request.getKiemHong().getToTruongXacNhan()) {
                    dashBoardCongViecCuaToi.status = "Tổ trưởng đã ký";
                }
                if (request.getKiemHong().getTroLyKTXacNhan()) {
                    dashBoardCongViecCuaToi.status = "Trợ lý KT đã ký";
                }
                if (request.getKiemHong().getQuanDocXacNhan()) {
                    dashBoardCongViecCuaToi.status = "Quản đốc đã ký";
                }
            } else if (dashBoardCongViecCuaToi.type == RequestType.DAT_HANG) {
                dashBoardCongViecCuaToi.status = "Quản đốc đã ký";
                if (request.getPhieuDatHang().getNguoiDatHangXacNhan()) {
                    dashBoardCongViecCuaToi.status = "Người đặt hàng đã ký";
                }
                if (request.getPhieuDatHang().getTpvatTuXacNhan()) {
                    dashBoardCongViecCuaToi.status = "T.P Vật Tư đã ký";
                }
                if (request.getPhieuDatHang().getTpkthkXacNhan()) {
                    dashBoardCongViecCuaToi.status = "Đặt Hàng thành công";
                }
            } else if (dashBoardCongViecCuaToi.type == RequestType.PHUONG_AN) {
                dashBoardCongViecCuaToi.status = "T.P KTHK đã ký";
//                if (request.getPhuongAn().getNguoiLapXacNhan()) {
//                    dashBoardCongViecCuaToi.status = "Người lập đã ký";
//                }
//                if (request.getPhuongAn().getTruongPhongKTHKXacNhan()) {
//                    dashBoardCongViecCuaToi.status = "T.P KTHK đã ký";
//                }
//                if (request.getPhuongAn().getTruongPhongVatTuXacNhan()) {
//                    dashBoardCongViecCuaToi.status = "T.P Vật tư đã ký";
//                }
//                if (request.getPhuongAn().getTruongPhongKeHoachXacNhan()) {
//                    dashBoardCongViecCuaToi.status = "T.P Kế Hoạch đã ký";
//                }
//                if (request.getPhuongAn().getGiamDocXacNhan()) {
//                    dashBoardCongViecCuaToi.status = "Giám Đốc đã ký";
//                }
            } else if (dashBoardCongViecCuaToi.type == RequestType.CONG_NHAN_THANH_PHAM) {
                dashBoardCongViecCuaToi.status = "Giám Đốc đã ký";
//                if (request.getCongNhanThanhPham().getNguoiThucHienXacNhan()) {
//                    dashBoardCongViecCuaToi.status = "Người thực hiện đã ký";
//                }
//                if (request.getCongNhanThanhPham().getNguoiGiaoViecXacNhan()) {
//                    dashBoardCongViecCuaToi.status = "Người giao việc đã ký";
//                }
//                if (request.getCongNhanThanhPham().getTpkcsXacNhan()) {
//                    dashBoardCongViecCuaToi.status = "T.P KCS đã ký";
//                }
            }
        } catch (Exception ex) {
            // DO no thing
        }

        return dashBoardCongViecCuaToi;
    }

    private static String getVal(Map<Long, User> unameById, Long key) {
        if (CollectionUtils.isEmpty(unameById)) {
            return key.toString();
        }
        Long k = Long.valueOf(key);
        if (unameById.containsKey(k)) {
            return unameById.get(k) == null ? key.toString() : unameById.get(k).getFullName();
        }
        return key.toString();
    }

    public static DashBoardCongViecCuaToi fromPhuongAn(PhuongAn el, Map<Long, User> userById) {
        DashBoardCongViecCuaToi dashBoardCongViecCuaToi = new DashBoardCongViecCuaToi();
        dashBoardCongViecCuaToi.ma = "Key-" + el.getPaId();
        dashBoardCongViecCuaToi.requestId = el.getPaId();
        dashBoardCongViecCuaToi.status = "Vừa tạo";
        if (el.getCntpReceiverId() == null) {
            dashBoardCongViecCuaToi.type = RequestType.PHUONG_AN;
            dashBoardCongViecCuaToi.setNoiDung(el.getNoiDung());
            if (el.getNguoiLapXacNhan()) {
                dashBoardCongViecCuaToi.status = "Người lập phiếu đã ký";
            }
            if (el.getTruongPhongKTHKXacNhan()) {
                dashBoardCongViecCuaToi.status = "Trưởng phòng đã ký";
            }
            if (el.getTruongPhongVatTuXacNhan()) {
                dashBoardCongViecCuaToi.status = "Phòng vật tư phiếu đã ký";
            }
            if (el.getTruongPhongKeHoachXacNhan()) {
                dashBoardCongViecCuaToi.status = " Phòng kế hoạch đã ký";
            }
            if (el.getGiamDocXacNhan()) {
                dashBoardCongViecCuaToi.status = " Giám đốc đã ký";
            }
        } else {
            dashBoardCongViecCuaToi.type = RequestType.CONG_NHAN_THANH_PHAM;

        }


        return dashBoardCongViecCuaToi;
    }

    public static DashBoardCongViecCuaToi fromCNTP(CongNhanThanhPham el, Map<Long, User> userById) {
        DashBoardCongViecCuaToi dashboard = new DashBoardCongViecCuaToi();
        dashboard.ma = "Key-" + el.getTpId();
        dashboard.requestId = el.getTpId();
        dashboard.status = "Vừa tạo";
        dashboard.noiDung = el.getNoiDung();
        dashboard.setType(RequestType.CONG_NHAN_THANH_PHAM);
        return dashboard;
    }
}



