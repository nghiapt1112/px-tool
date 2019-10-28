package com.px.tool.domain.phuongan;

import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class DinhMucLaoDongPayload extends AbstractObject {
    public Long dmId;

    private String tt;

    private String noiDungCongViec;

    private String bacCV;

    private String dm;

    private String ghiChu;

    public DinhMucLaoDong toEntity() {
        DinhMucLaoDong dinhMucLaoDong = new DinhMucLaoDong();
        BeanUtils.copyProperties(this, dinhMucLaoDong);
        return dinhMucLaoDong;
    }

    public static DinhMucLaoDongPayload fromEntity(DinhMucLaoDong dinhMucLaoDong) {
        DinhMucLaoDongPayload dinhMucLaoDongPayload = new DinhMucLaoDongPayload();
        BeanUtils.copyProperties(dinhMucLaoDong, dinhMucLaoDongPayload);
        return dinhMucLaoDongPayload;
    }
}
