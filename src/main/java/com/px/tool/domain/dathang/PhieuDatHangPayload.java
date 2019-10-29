package com.px.tool.domain.dathang;

import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.HashSet;
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
    private String noiNhan;
    private String ngayThangNamTPKTHK;
    private String TPKTHK;
    private String ngayThangNamTPVatTu;
    private String TPVatTu;
    private String ngayThangNamNguoiDatHang;
    private String NguoiDatHang;
    private String yKienGiamDoc;
    private Long chuyen; // id cua user dc nhan
    private Set<PhieuDatHangDetailPayload> phieuDatHangDetails = new HashSet<>();

    public static PhieuDatHangPayload fromEntity(PhieuDatHang phieuDatHang) {
        PhieuDatHangPayload phieuDatHangPayload = new PhieuDatHangPayload();
        BeanUtils.copyProperties(phieuDatHang, phieuDatHangPayload);
        phieuDatHangPayload.setPhieuDatHangDetails(
                phieuDatHang.getPhieuDatHangDetails()
                        .stream()
                        .map(PhieuDatHangDetailPayload::fromEntity)
                        .collect(Collectors.toSet())
        );
        return phieuDatHangPayload;
    }

    public PhieuDatHang toEntity() {
        PhieuDatHang phieuDatHang = new PhieuDatHang();
        if (pdhId != null && pdhId <= 0) {
            pdhId = null;
        }
        BeanUtils.copyProperties(this, phieuDatHang);
        phieuDatHang.setPhieuDatHangDetails(
                phieuDatHangDetails.stream()
                        .map(PhieuDatHangDetailPayload::toEntity)
                        .collect(Collectors.toSet())
        );
        return phieuDatHang;
    }

}
