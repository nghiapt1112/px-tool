package com.px.tool.domain.vanbanden.payload;

import com.px.tool.domain.vanbanden.VanBanDen;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VanBanDenDetail {
    private Long vbdId;
    private Long noiNhan;
    private String noiDung;
    private List<String> files;

    public static VanBanDenDetail fromEntity(VanBanDen vanBanDen) {
        VanBanDenDetail payload = new VanBanDenDetail();
        payload.vbdId = vanBanDen.getVbdId();
        payload.noiNhan = vanBanDen.getNoiNhan();
        payload.noiDung = vanBanDen.getNoiDung();
        return payload;
    }

    public VanBanDenDetail withFilesName(List<String> listFile) {
        this.files = listFile;
        return this;
    }
}
