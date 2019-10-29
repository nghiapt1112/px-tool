package com.px.tool.domain.kiemhong;

import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class KiemHongPayLoad extends AbstractObject {
    private Long requestId;
    private Long khId;
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

    private Set<KiemHongDetailPayload> kiemHongDetails = new HashSet<>();
    private Long chuyen; // id cua user dc nhan

    public KiemHongPayLoad andRequestId(Long requestId) {
        this.requestId = requestId;
        return this;
    }

    public static KiemHongPayLoad fromEntity(KiemHong kiemHong) {
        KiemHongPayLoad kiemHongResponse = new KiemHongPayLoad();
        BeanUtils.copyProperties(kiemHong, kiemHongResponse);
        kiemHongResponse.kiemHongDetails = kiemHong.getKiemHongDetails()
                .stream()
                .map(KiemHongDetailPayload::fromEntity)
                .collect(Collectors.toSet());
        return kiemHongResponse;
    }

    public KiemHong toEntity() {
        KiemHong kiemHong = new KiemHong();
        BeanUtils.copyProperties(this, kiemHong);
        kiemHong.setKiemHongDetails(
                this.kiemHongDetails
                        .stream()
                        .map(KiemHongDetailPayload::toEntity)
                        .collect(Collectors.toSet()));
        return kiemHong;
    }
}
