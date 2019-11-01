package com.px.tool.domain.kiemhong;

import com.px.tool.infrastructure.model.request.AbstractObject;
import com.px.tool.infrastructure.utils.DateTimeUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;

import java.time.DateTimeException;
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
    private Boolean quanDocXacNhan;

    private String ngayThangNamTroLyKT;
    private String troLyKT;
    private Boolean troLyKTXacNhan;

    private String ngayThangNamToTruong;
    private String toTruong;
    private Boolean toTruongXacNhan;

    private Boolean giamDocXacNhan;
    private String yKienGiamDoc;

    private Set<KiemHongDetailPayload> kiemHongDetails = new HashSet<>();
    private Long chuyen; // id cua user dc nhan

    public static KiemHongPayLoad fromEntity(KiemHong kiemHong) {
        KiemHongPayLoad kiemHongResponse = new KiemHongPayLoad();
        BeanUtils.copyProperties(kiemHong, kiemHongResponse);
        kiemHongResponse.kiemHongDetails = kiemHong.getKiemHongDetails()
                .stream()
                .map(KiemHongDetailPayload::fromEntity)
                .collect(Collectors.toSet());

        kiemHongResponse.ngayThangNamQuanDoc = DateTimeUtils.nowAsString();
        kiemHongResponse.ngayThangNamToTruong = DateTimeUtils.nowAsString();
        kiemHongResponse.ngayThangNamTroLyKT = DateTimeUtils.nowAsString();

        return kiemHongResponse;
    }

    public KiemHongPayLoad andRequestId(Long requestId) {
        this.requestId = requestId;
        return this;
    }

    public KiemHong toEntity(KiemHong kiemHong) {
        if (khId != null && khId <= 0) {
            khId = null;
        }
        BeanUtils.copyProperties(this, kiemHong);
        kiemHong.setKiemHongDetails(
                this.kiemHongDetails
                        .stream()
                        .map(payload -> {
                            KiemHongDetail entity = payload.toEntity();
                            if (kiemHong.getKhId() != null) {
                                entity.setKiemHong(kiemHong);
                            }
                            return entity;
                        })
                        .collect(Collectors.toSet()));
        if (CollectionUtils.isEmpty(kiemHong.getKiemHongDetails())) {
            throw new RuntimeException("Thieu thong tin detail cua kiem hong");
        }
        return kiemHong;
    }

    public boolean notIncludeId() {
        return khId != null && khId <= 0;
    }
}
