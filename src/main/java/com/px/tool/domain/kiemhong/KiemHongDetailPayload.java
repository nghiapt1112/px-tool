package com.px.tool.domain.kiemhong;

import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class KiemHongDetailPayload extends AbstractObject {
    public Long khDetailId;

    private String tt;

    private String tenPhuKien;

    private String tenLinhKien;

    private String kyHieu;

    private String sl;

    private String dangHuHong;

    private String phuongPhapKhacPhuc;

    private String nguoiKiemHong;

    public static KiemHongDetailPayload fromEntity(KiemHongDetail kiemHongDetail) {
        KiemHongDetailPayload kiemHongDetailPayload = new KiemHongDetailPayload();
        BeanUtils.copyProperties(kiemHongDetail, kiemHongDetailPayload);
        return kiemHongDetailPayload;
    }

    public KiemHongDetail toEntity() {
        if (khDetailId != null && khDetailId <= 0) {
            khDetailId = null;
        }
        KiemHongDetail kiemHongDetail = new KiemHongDetail();
        BeanUtils.copyProperties(this, kiemHongDetail);
        return kiemHongDetail;
    }
}
