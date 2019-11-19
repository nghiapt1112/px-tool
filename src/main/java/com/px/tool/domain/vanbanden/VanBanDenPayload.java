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
    private Long vbdId;
    private String noiNhan;
    private String noiDung;
    private List<String> files;

    public static VanBanDenPayload fromEntity(VanBanDen vanBanDen) {
        VanBanDenPayload payload = new VanBanDenPayload();
        BeanUtils.copyProperties(vanBanDen, payload);
        return payload;
    }

    public VanBanDen toEntity() {
        VanBanDen vanBanDen = new VanBanDen();
        BeanUtils.copyProperties(this, vanBanDen);
        try{
            vanBanDen.setNoiNhan(Long.valueOf(this.noiNhan));
        } catch (Exception ex) {

        }
        return vanBanDen;
    }


    public VanBanDenPayload withFilesName(List<String> listFile) {
        this.files = listFile;
        return this;
    }
}
