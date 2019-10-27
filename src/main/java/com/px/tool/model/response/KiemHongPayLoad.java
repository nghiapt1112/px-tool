package com.px.tool.model.response;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.px.tool.model.AbstractObject;
import com.px.tool.model.KiemHong;
import com.px.tool.model.KiemHongDetail;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class KiemHongPayLoad extends AbstractObject {
    public Long khId;
    private String tenNhaMay;
    private String phanXuong;
    private String toSX;
    private String tenVKTBKT; // may bay L39
    private String nguonVao;// SCL TTNH
    private String congDoan; // kiem hong chi tiet
    private String soHieu; // 8843
    private String soXX; // 8373y64
    private String toSo;
    private String soTo;
    private String noiNhan;
    private String ngayThangNamQuanDoc;
    private String quanDoc;
    private boolean quanDocXacNhan;

    private String ngayThangNamTroLyKT;
    private String troLyKT;
    private boolean troLyKTXacNhan;

    private String ngayThangNamToTruong;
    private String toTruong;
    private boolean toTruongXacNhan;

    private String yKienGiamDoc;

    @JsonManagedReference
    private Set<KiemHongDetail> kiemHongDetails = new HashSet<>();

    public static KiemHongPayLoad fromEntity(KiemHong kiemHong) {
        kiemHong.setUpdatedAt(null);
        kiemHong.setUpdatedBy(null);
        KiemHongPayLoad kiemHongResponse = new KiemHongPayLoad();
        BeanUtils.copyProperties(kiemHong, kiemHongResponse);
        return kiemHongResponse;
    }
    public void toEntity(@NotNull KiemHong kiemHong) {
        BeanUtils.copyProperties(this, kiemHong);
    }
}
