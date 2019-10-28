package com.px.tool.domain.dathang;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "phieu_dat_hang_detail")
public class PhieuDatHangDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long pdhDetailId;

    @Column
    private String stt;

    @Column
    private String tenPhuKien;

    @Column
    private String tenVatTuKyThuat;

    @Column
    private String kiMaHieu;

    @Column
    private String dvt;

    @Column
    private String sl;

    @Column
    private String mucDichSuDung;

    @Column
    private String phuongPhapKhacPhuc;

    @Column
    private String soPhieuDatHang;

    @Column
    private String nguoiThucHien;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "pdhId", insertable = false, updatable = false)
    private PhieuDatHang phieuDatHang;
}
