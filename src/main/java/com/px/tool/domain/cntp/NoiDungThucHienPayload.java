package com.px.tool.domain.cntp;

import com.px.tool.infrastructure.model.payload.AbstractObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class NoiDungThucHienPayload extends AbstractObject {
    private Long noiDungId;

    private String noiDung;

    private String ketQua;

    private String nguoiLam;

    private String nghiemThu;
    private Long chuyen; // id cua user dc nhan

    public static NoiDungThucHienPayload fromEntity(NoiDungThucHien noiDungThucHien) {
        NoiDungThucHienPayload noiDungThucHienPayload = new NoiDungThucHienPayload();
        BeanUtils.copyProperties(noiDungThucHien, noiDungThucHienPayload);
        return noiDungThucHienPayload;
    }

    public NoiDungThucHien toEntity() {
        NoiDungThucHien noiDungThucHien = new NoiDungThucHien();
//        if (noiDungId != null && noiDungId <= 0) {
        noiDungId = null;
//        }
        BeanUtils.copyProperties(this, noiDungThucHien);
        return noiDungThucHien;
    }
}
