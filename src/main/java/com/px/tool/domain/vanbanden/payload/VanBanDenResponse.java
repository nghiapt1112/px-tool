package com.px.tool.domain.vanbanden.payload;

import com.px.tool.domain.vanbanden.VanBanDen;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VanBanDenResponse {
    private Long vbdId;
    private String noiNhan;
    private String noiDung;
    private Long folder;
    private List<String> files;

    public static VanBanDenResponse fromEntity(VanBanDen vanBanDen) {
        VanBanDenResponse payload = new VanBanDenResponse();
        payload.vbdId = vanBanDen.getVbdId();
        payload.noiNhan = vanBanDen.getNoiNhan().toString();
        payload.noiDung = vanBanDen.getNoiDung();
        payload.folder = vanBanDen.getFolder();
        return payload;
    }

    public VanBanDenResponse withFilesName(List<String> listFile) {
        this.files = listFile;
        return this;
    }
}

