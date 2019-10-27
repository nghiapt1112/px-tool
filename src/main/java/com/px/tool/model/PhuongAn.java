package com.px.tool.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "phuong_an")
public class PhuongAn extends EntityDefault {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long paId;

    @Column
    private String tenNhaMay;

    @Column
    private String ngayThangNamPheDuyet;

    @Column
    private String maSo;

    @Column
    private String sanPham;

    @Column
    private String noiDung;

    @Column
    private String nguonKinhPhi;

    @Column
    private String toSo;

    @Column
    private String soTo;

    @Column
    private String PDH;

    //end
    @Column
    private String ngayThangNamTPKTHK;

    @Column
    private String truongPhongKTHK;

    @Column
    private String ngayThangNamTPKEHOACH;

    @Column
    private String truongPhongKeHoach;

    @Column
    private String ngayThangNamtpVatTu;

    @Column
    private String truongPhongVatTu;

    @Column
    private String ngayThangNamNguoiLap;

    @Column
    private String NguoiLap;

    // cac field tong cong
    @Column
    private BigDecimal tongCongDinhMucLaoDong;

    @Column
    private BigDecimal tongDMVTKho;

    @Column
    private BigDecimal tongDMVTMuaNgoai;

    @Column
    private BigDecimal tienLuong;

    @JsonManagedReference
    @OneToMany(mappedBy = "phuongAn", cascade = CascadeType.ALL)
    private Set<DinhMucLaoDong> dinhMucLaoDongs = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "phuongAn", cascade = CascadeType.ALL)
    private Set<DinhMucVatTu> dinhMucVatTus = new HashSet<>();

//    @OneToOne(mappedBy = "phuongAn")
//    private Request request;
    //TODO: con thieu vai fields nua, fields ben duoi cho chuyen chuyen y
}
