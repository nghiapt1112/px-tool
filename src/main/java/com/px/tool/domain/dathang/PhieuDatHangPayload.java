package com.px.tool.domain.dathang;

import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class PhieuDatHangPayload extends AbstractObject {
    public Long pdhId;
    private String tenNhaMay;
    private String tenPhong;
    private String so;
    private String donViYeuCau;
    private String phanXuong;
    private String noiDung;
    private String noiNhan;
    private String ngayThangNamTPKTHK;
    private String TPKTHK;
    private String ngayThangNamTPVatTu;
    private String TPVatTu;
    private String ngayThangNamNguoiDatHang;
    private String NguoiDatHang;
    private String yKienGiamDoc;

    private Set<PhieuDatHangDetail> phieuDatHangDetails = new HashSet<>();

    public void toEntity(PhieuDatHang phieuDatHang) {
        BeanUtils.copyProperties(this, phieuDatHang);
    }

}
