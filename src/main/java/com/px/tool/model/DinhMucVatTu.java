package com.px.tool.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DinhMucVatTu extends PhuongAn {
    private String tt;
    private String tenVatTuKyThuat;
    private String kyMaKyHieu;
    private String dvt;
    private String dm1SP;
    private String soLuongSanPham;
    private String tongNhuCau;
    // huy dong kho
    private String khoDonGia;
    private String khoSoLuong;
    private String khoThanhTien;
    private String khoTongTien;

    //mua ngoai
    private String mnDonGia;
    private String mnSoLuong;
    private String mnThanhTien;
    private String mnTongTien;

    private String ghiChu;
    private String tienLuong;

    //end
    private String ngayThangNamTPKTHK;
    private String truongPhongKTHK;
    private String ngayThangNamTPKEHOACH;
    private String truongPhongKeHoach;
    private String ngayThangNamtpVatTu;
    private String truongPhongVatTu;
    private String ngayThangNamNguoiLap;
    private String NguoiLap;

    //TODO: con thieu vai fields nua
}
