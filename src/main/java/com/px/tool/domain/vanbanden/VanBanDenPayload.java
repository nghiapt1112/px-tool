package com.px.tool.domain.vanbanden;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Getter
@Setter
@ToString
public class VanBanDenPayload {
    public Long vbdId;
    public Long noiNhan;
    public String noiDung;
    public List<String> files;

    public static VanBanDenPayload fromEntity(VanBanDen vanBanDen) {
        VanBanDenPayload payload = new VanBanDenPayload();
        BeanUtils.copyProperties(vanBanDen, payload);
        return payload;
    }

    public VanBanDen toEntity() {
        VanBanDen vanBanDen = new VanBanDen();
        BeanUtils.copyProperties(this, vanBanDen);
        return vanBanDen;
    }


}
