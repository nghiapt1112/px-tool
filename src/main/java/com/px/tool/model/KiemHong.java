package com.px.tool.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KiemHong extends AbstractObject {
    private String tenNhaMay;
    private String phanXuong;
    private String toSX;
    private String tenVKTBKT; // may bay L39
    private String nguonVao;// SCL TTNH
    private String congDoan; // kiem hong chi tiet
    private String soHieu; // 8843
    private String soXX; // 8373y64
    private String toSo;
    private String soTo;

// table
    private String tt;
    private String tenPhuKien;
    private String tenLinhKien;
    private String kyHieu;
    private String sl;
    private String dangHuHong;
    private String phuongPhapKhacPhuc;
    private String nguoiKiemHong;

//
    private String noiNhan;
    private String ngayThangNamQuanDoc;
    private String quanDoc;
    private String ngayThangNamTroLyKT;
    private String troLyKT;
    private String ngayThangNamToTruong;
    private String toTruong;
    private String yKienGiamDoc;
}
