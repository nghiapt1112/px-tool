package com.px.tool.domain.dathang;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.px.tool.domain.user.User;
import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class PhieuDatHangPayload extends AbstractObject {
    private Long requestId;
    private Long pdhId;
    private String tenNhaMay;
    private String tenPhong;
    private String so;
    private String donViYeuCau;
    private String phanXuong;
    private String noiDung;
    private Long noiNhan;
    private String ngayThangNamTPKTHK;
    private String TPKTHK;
    private Boolean tpkthkXacNhan;
    private String ngayThangNamTPVatTu;
    private String TPVatTu;
    private Boolean tpvatTuXacNhan;
    private String ngayThangNamNguoiDatHang;
    private String NguoiDatHang;
    private Boolean nguoiDatHangXacNhan;

    private boolean nguoiDatHangDisable;
    private boolean tpvatTuDisable;
    private boolean tpkthkDisable;


    @JsonProperty("yKienNguoiDatHang")
    private String yKienNguoiDatHang;

    @JsonProperty("yKienTPKTHK")
    private String yKienTPKTHK;

    @JsonProperty("yKienTPVatTu")
    private String yKienTPVatTu;
    private Long chuyen; // id cua user dc nhan
    private Set<PhieuDatHangDetailPayload> phieuDatHangDetails = new HashSet<>();

    // chuky + ten
    private String nguoiDatHangSignImg;
    private String tpvatTuSignImg;
    private String tpkthkSignImg;

    private String nguoiDatHangFullName;
    private String tpvatTuFullName;
    private String tpkthkFullName;

    private Long nguoiDatHangId;
    private Long tpvatTuId;
    private Long tpkthkId;

    public static PhieuDatHangPayload fromEntity(PhieuDatHang phieuDatHang) {
        PhieuDatHangPayload phieuDatHangPayload = new PhieuDatHangPayload();
        BeanUtils.copyProperties(phieuDatHang, phieuDatHangPayload);
        phieuDatHangPayload.setPhieuDatHangDetails(
                phieuDatHang.getPhieuDatHangDetails()
                        .stream()
                        .map(PhieuDatHangDetailPayload::fromEntity)
                        .collect(Collectors.toSet())
        );
        phieuDatHangPayload.setNoiNhan(null);
        return phieuDatHangPayload;
    }

    public PhieuDatHang toEntity(PhieuDatHang phieuDatHang) {
        if (pdhId != null && pdhId <= 0) {
            pdhId = null;
        }
        BeanUtils.copyProperties(this, phieuDatHang);
        phieuDatHang.setPhieuDatHangDetails(
                phieuDatHangDetails.stream()
                        .map(payload -> {
                            PhieuDatHangDetail entity = payload.toEntity();
                            if (Objects.nonNull(phieuDatHang.getPdhId())) {
                                entity.setPhieuDatHang(phieuDatHang);
                            }
                            return entity;
                        })
                        .collect(Collectors.toSet())
        );
        return phieuDatHang;
    }

    public boolean notIncludeId() {
        return pdhId != null && pdhId <= 0;
    }

    public Set<Long> getDetailIds() {
        return phieuDatHangDetails.stream()
                .map(PhieuDatHangDetailPayload::getPdhDetailId)
                .collect(Collectors.toSet());
    }

    public PhieuDatHangPayload filterPermission(User currentUser) {
        nguoiDatHangDisable = true;
        tpkthkDisable = true;
        tpvatTuDisable = true;
        if (currentUser.isNhanVienVatTu()) {
            nguoiDatHangDisable = false;
        } else if (currentUser.isTruongPhongVatTu()) {
            tpvatTuDisable = false;
        } else if (currentUser.isTruongPhongKTHK()) {
            tpkthkDisable = false;
        }
        return this;
    }

    public void capNhatChuKy(User user) {
        if (user.isNhanVienVatTu() && nguoiDatHangXacNhan) {
            this.nguoiDatHangId = user.getUserId();
        }
        if (user.isTruongPhongVatTu() && tpvatTuXacNhan) {
            tpvatTuId = user.getUserId();
        }
        if (user.isTruongPhongKTHK() && tpkthkXacNhan) {
            tpkthkId = user.getUserId();
        }
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
