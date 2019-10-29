package com.px.tool.domain.dathang;

import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class PhieuDatHangDetailPayload extends AbstractObject {
    public Long pdhDetailId;
    private String stt;
    private String tenPhuKien;
    private String tenVatTuKyThuat;
    private String kiMaHieu;
    private String dvt;
    private String sl;
    private String mucDichSuDung;
    private String phuongPhapKhacPhuc;
    private String soPhieuDatHang;
    private String nguoiThucHien;

    public static PhieuDatHangDetailPayload fromEntity(PhieuDatHangDetail phieuDatHangDetail) {
        PhieuDatHangDetailPayload phieuDatHangDetailPayload = new PhieuDatHangDetailPayload();
        BeanUtils.copyProperties(phieuDatHangDetail, phieuDatHangDetailPayload);
        return phieuDatHangDetailPayload;
    }

    public PhieuDatHangDetail toEntity() {
        PhieuDatHangDetail phieuDatHangDetail = new PhieuDatHangDetail();
        if (pdhDetailId <= 0) {
            pdhDetailId = null;
        }
        BeanUtils.copyProperties(this, phieuDatHangDetail);
        return phieuDatHangDetail;
    }

}
