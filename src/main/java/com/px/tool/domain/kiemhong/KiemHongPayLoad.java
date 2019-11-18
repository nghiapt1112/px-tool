package com.px.tool.domain.kiemhong;

import com.px.tool.domain.user.User;
import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
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
    private Long noiNhan;
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

    private List<KiemHongDetailPayload> kiemHongDetails = new LinkedList<>();

    // Permission to edit chu ky:
    private boolean quanDocDisable;
    private boolean troLyKTDisable;
    private boolean toTruongDisable;

    private String yKienQuanDoc;
    private String yKienToTruong;
    private String yKienTroLyKT;

    public static KiemHongPayLoad fromEntity(KiemHong kiemHong) {
        KiemHongPayLoad kiemHongResponse = new KiemHongPayLoad();
        BeanUtils.copyProperties(kiemHong, kiemHongResponse);
        kiemHongResponse.kiemHongDetails = kiemHong.getKiemHongDetails()
                .stream()
                .map(KiemHongDetailPayload::fromEntity)
                .sorted(Comparator.comparingLong(KiemHongDetailPayload::getKhDetailId))
                .collect(Collectors.toCollection(LinkedList::new));

        if (kiemHong.getRequest() != null) {
            kiemHongResponse.noiNhan = kiemHong.getRequest().getKiemHongReceiverId();
        }
        kiemHongResponse.quanDocDisable = true;
        kiemHongResponse.troLyKTDisable = true;
        kiemHongResponse.toTruongDisable = true;
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
        return kiemHong;
    }

    public boolean includedId() {
        return khId != null && khId > 0;
    }

    public Boolean getQuanDocXacNhan() {
        return quanDocXacNhan == null ? false : quanDocXacNhan;
    }

    public Boolean getTroLyKTXacNhan() {
        return troLyKTXacNhan == null ? false : troLyKTXacNhan;
    }

    public Boolean getToTruongXacNhan() {
        return toTruongXacNhan == null ? false : toTruongXacNhan;
    }

    public Boolean getGiamDocXacNhan() {
        return giamDocXacNhan == null ? false : giamDocXacNhan;
    }

    public KiemHongPayLoad filterPermission(User currentUser) {
        troLyKTDisable = true;
        toTruongDisable = true;
        quanDocDisable = true;
        if (currentUser.isTroLyKT()) {
            this.troLyKTDisable = false;
        } else if (currentUser.isToTruong()) {
            toTruongDisable = false;
        } else if (currentUser.isQuanDocPhanXuong()) {
            quanDocDisable = false;
        }
        return this;
    }
}
