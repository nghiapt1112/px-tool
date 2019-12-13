package com.px.tool.domain.vanbanden.payload;

import com.px.tool.domain.vanbanden.VanBanDen;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class VanBanDenRequest {
    private Long vbdId;
    private Long noiNhan;
    private String soPa;
    private String noiDung;
    private List<String> files;

    public static VanBanDenRequest fromEntity(VanBanDen vanBanDen) {
        VanBanDenRequest payload = new VanBanDenRequest();
        payload.vbdId = vanBanDen.getVbdId();
        payload.noiNhan = vanBanDen.getNoiNhan();
        payload.noiDung = vanBanDen.getNoiDung();
        return payload;
    }

    public VanBanDen toEntity() {
        VanBanDen vanBanDen = new VanBanDen();
        vanBanDen.setVbdId(this.vbdId);
        vanBanDen.setNoiDung(this.noiDung);
        try {
            vanBanDen.setNoiNhan(Long.valueOf(this.noiNhan));
        } catch (Exception ex) {

        }
        return vanBanDen;
    }

}
