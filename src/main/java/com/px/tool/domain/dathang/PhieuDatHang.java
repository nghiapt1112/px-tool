package com.px.tool.domain.dathang;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.px.tool.infrastructure.model.request.EntityDefault;
import com.px.tool.domain.request.Request;
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
@Table(name = "phieu_dat_hang")
public class PhieuDatHang extends EntityDefault {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long pdhId;

    @Column
    private String tenNhaMay;

    @Column
    private String tenPhong;

    @Column
    private String so;

    @Column
    private String donViYeuCau;

    @Column
    private String phanXuong;

    @Column
    private String noiDung;

    @Column
    private String noiNhan;

    @Column
    private String ngayThangNamTPKTHK;

    @Column
    private String TPKTHK;

    @Column
    private String ngayThangNamTPVatTu;

    @Column
    private String TPVatTu;

    @Column
    private String ngayThangNamNguoiDatHang;

    @Column
    private String NguoiDatHang;

    @Column
    private String yKienGiamDoc;

    @JsonManagedReference
    @OneToMany(mappedBy = "phieuDatHang", cascade = CascadeType.ALL)
    private Set<PhieuDatHangDetail> phieuDatHangDetails = new HashSet<>();

    @JsonBackReference
    @OneToOne(mappedBy = "phieuDatHang")
    private Request request;
}