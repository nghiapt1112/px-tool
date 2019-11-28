package com.px.tool.domain.cntp;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.px.tool.domain.request.Request;
import com.px.tool.infrastructure.model.request.EntityDefault;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "cong_nhan_thanh_pham")
public class CongNhanThanhPham extends EntityDefault {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tpId; // thanh pham id
    @Column
    private String tenSanPham;

    @Column
    private String noiDung;

    @Column
    private String soPA;

    @Column
    private String donviThucHien;

    @Column
    private String donviDatHang;

    @Column
    private String soNghiemThuDuoc;

    @Column
    private Boolean nguoiGiaoViecXacNhan;

    @Column
    private Boolean nguoiThucHienXacNhan;

    @Column
    private Boolean tpkcsXacNhan;

    @Column
    private Float dong;

    @Column
    private Float gioX;

    @Column
    private Float laoDongTienLuong;

    @Column
    private String dvt;

    @Column
    private String to;

    @Column
    private String soLuong;

    @Column
    private String yKienNguoiGiaoViec;

    @Column
    private String yKienNguoiThucHien;

    @Column
    private String yKienTPKCS;

    @Column
    private String yKienTpkcsXacNhan;


    @Column
    private String ngayThangNamNguoiThucHien;

    @Column
    private String ngayThangNamTPKCS;

    @JsonManagedReference
    @OneToMany(mappedBy = "congNhanThanhPham", cascade = CascadeType.ALL)
    private Set<NoiDungThucHien> noiDungThucHiens = new HashSet<>();

    @JsonBackReference
    @OneToOne(mappedBy = "congNhanThanhPham")
    private Request request;

}
