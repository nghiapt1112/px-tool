package com.px.tool.domain.phuongan;

import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class DinhMucVatTuPayload extends AbstractObject {
    public Long vtId;

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

    public static DinhMucVatTuPayload fromEntity(DinhMucVatTu dinhMucVatTu) {
        DinhMucVatTuPayload dinhMucVatTuPayload = new DinhMucVatTuPayload();
        BeanUtils.copyProperties(dinhMucVatTu, dinhMucVatTuPayload);
        return dinhMucVatTuPayload;
    }

    public DinhMucVatTu toEntity() {
        DinhMucVatTu dinhMucVatTu = new DinhMucVatTu();
        if (vtId != null && vtId <= 0) {
            vtId = null;
        }
        BeanUtils.copyProperties(this, dinhMucVatTu);
        return dinhMucVatTu;
    }

}
