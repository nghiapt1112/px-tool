package com.px.tool.domain.request;

import com.px.tool.domain.dathang.PhieuDatHangDetail;
import com.px.tool.domain.kiemhong.KiemHongDetail;
import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
public class ThongKeDetailPayload extends AbstractObject {
    public Long tt;
    public String tenPhuKien;
    public String tenLinhKien;
    public String kyHieu;
    public Long SL;
    public String dangHuHong;
    public String ngayKiemHong;
    public String phuongPhapKhacPhuc;
    public String ngayChuyenPhongVatTu;
    public String soPhieuDatHang;
    public String ngayChuyenKT;
    public String soPA;
    public String ngayRaPA;
    public String ngayChuyenKH;
    public String ngayPheDuyet;
    public String ngayHoanThanh;
    public String xacNhanHoanThanh;
    public Long requestId;

    public static ThongKeDetailPayload fromRequestEntity(Request request) {
        ThongKeDetailPayload tk = null;
        Map<Long, PhieuDatHangDetail> pdhDetailById = new HashMap<>();
        for (PhieuDatHangDetail phieuDatHangDetail : request.getPhieuDatHang().getPhieuDatHangDetails()) {
            pdhDetailById.put(phieuDatHangDetail.getKiemHongDetailId(), phieuDatHangDetail);
        }

        PhieuDatHangDetail pdhDt = null;
        for (KiemHongDetail detail : request.getKiemHong().getKiemHongDetails()) {
            //        tk.tt =
            tk = new ThongKeDetailPayload();
            tk.tenPhuKien = detail.getTenPhuKien();
            tk.tenLinhKien = detail.getTenLinhKien();
            tk.kyHieu = detail.getKyHieu();
            try {
                tk.SL = Long.valueOf(detail.getSl() != null ? detail.getSl() : "0");
            } catch (NumberFormatException e) {
                tk.SL = 0L;
            }
            tk.dangHuHong = detail.getDangHuHong();
            tk.ngayKiemHong = "detail .get() ngay kiem hong";
            tk.phuongPhapKhacPhuc = detail.getPhuongPhapKhacPhuc();
            tk.ngayChuyenPhongVatTu = "11/12/1993";
            tk.soPhieuDatHang = "PDH";
            try {
                pdhDt = pdhDetailById.get(detail.getKhDetailId());
                tk.soPhieuDatHang = pdhDt.getSoPhieuDatHang();
            } catch (Exception e) {

            }
            tk.ngayChuyenKT = request.getPhieuDatHang().getNgayThangNamTPVatTu();
            tk.soPA = "PA";
            tk.ngayRaPA = request.getPhuongAn().getNgayThangNamNguoiLap();
            tk.ngayChuyenKH = request.getPhuongAn().getNgayThangNamtpVatTu();
            tk.ngayPheDuyet = request.getPhuongAn().getNgayThangNamTPKTHK();
//            tk.ngayHoanThanh = request.getCongNhanThanhPham().getNguoiThucHienXacNhan(); // TODO: thieu field nay
            tk.xacNhanHoanThanh = request.getStatus().name();
            tk.requestId = request.getRequestId();
        }
        return tk;
    }
}
