package com.px.tool.domain.dathang;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.px.tool.domain.request.Request;
import com.px.tool.infrastructure.model.payload.EntityDefault;
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
import java.util.Objects;
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
    private Long noiNhan;

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

    @Column(name = "tpkthk_xac_nhan")
    private Boolean tpkthkXacNhan;

    @Column(name = "tpvattu_xac_nhan")
    private Boolean tpvatTuXacNhan;

    @Column(name = "nguoi_dat_hang_xac_nhan")
    private Boolean nguoiDatHangXacNhan;

    @Column
    private String yKienNguoiDatHang;

    @Column
    private String yKienTPKTHK;

    @Column
    private String yKienTPVatTu;

    // sign id

    @Column
    private Long nguoiDatHangId;

    @Column
    private Long tpvatTuId;

    @Column
    private Long tpkthkId;

    // update flow moi
    private Long trolyKT; // cac tro ly nay thuoc 8,9 level = 4

    @JsonManagedReference
    @OneToMany(mappedBy = "phieuDatHang", cascade = CascadeType.ALL)
    private Set<PhieuDatHangDetail> phieuDatHangDetails = new HashSet<>();

    @JsonBackReference
    @OneToOne(mappedBy = "phieuDatHang")
    private Request request;

    public boolean allApproved() {
        return
                Objects.nonNull(tpkthkXacNhan) && tpkthkXacNhan &&
                        Objects.nonNull(tpvatTuXacNhan) && tpvatTuXacNhan &&
                        Objects.nonNull(nguoiDatHangXacNhan) && nguoiDatHangXacNhan;
    }

    public Boolean getTpkthkXacNhan() {
        return tpkthkXacNhan == null ? false : tpkthkXacNhan;
    }

    public Boolean getTpvatTuXacNhan() {
        return tpvatTuXacNhan == null ? false : tpvatTuXacNhan;
    }

    public Boolean getNguoiDatHangXacNhan() {
        return nguoiDatHangXacNhan == null ? false : nguoiDatHangXacNhan;
    }
}
