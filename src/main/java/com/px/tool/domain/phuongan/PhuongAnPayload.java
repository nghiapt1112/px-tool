package com.px.tool.domain.phuongan;

import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class PhuongAnPayload extends AbstractObject {
    private Long paId;
    private Long requestId;
    private String tenNhaMay;
    private String ngayThangNamPheDuyet;
    private String maSo;
    private String sanPham;
    private String noiDung;
    private String nguonKinhPhi;
    private String toSo;
    private String soTo;
    private String PDH;    //end

    private String ngayThangNamTPKTHK;
    private String truongPhongKTHK;
    private String ngayThangNamTPKEHOACH;
    private String truongPhongKeHoach;
    private String ngayThangNamtpVatTu;
    private String truongPhongVatTu;
    private String ngayThangNamNguoiLap;
    private String NguoiLap;    // cac field tong cong

    private BigDecimal tongCongDinhMucLaoDong;
    private BigDecimal tongDMVTKho;
    private BigDecimal tongDMVTMuaNgoai;
    private BigDecimal tienLuong;
    private Set<DinhMucLaoDongPayload> dinhMucLaoDongs = new HashSet<>();
    private Set<DinhMucVatTuPayload> dinhMucVatTus = new HashSet<>();
    private Long chuyen; // id cua user dc nhan

    public static PhuongAnPayload fromEntity(PhuongAn phuongAn) {
        PhuongAnPayload phuongAnPayload = new PhuongAnPayload();
        BeanUtils.copyProperties(phuongAn, phuongAnPayload);
        phuongAnPayload.dinhMucLaoDongs = phuongAn.getDinhMucLaoDongs().stream()
                .map(DinhMucLaoDongPayload::fromEntity)
                .collect(Collectors.toSet());
        phuongAnPayload.dinhMucVatTus = phuongAn.getDinhMucVatTus().stream()
                .map(DinhMucVatTuPayload::fromEntity)
                .collect(Collectors.toSet());
        return phuongAnPayload;
    }

    public PhuongAn toEntity() {
        PhuongAn phuongAn = new PhuongAn();
        if (paId <= 0) {
            paId = null;
        }
        BeanUtils.copyProperties(this, phuongAn);
        phuongAn.setDinhMucLaoDongs(
                dinhMucLaoDongs
                        .stream()
                        .map(DinhMucLaoDongPayload::toEntity)
                        .collect(Collectors.toSet())
        );
        phuongAn.setDinhMucVatTus(
                dinhMucVatTus.stream()
                        .map(DinhMucVatTuPayload::toEntity)
                        .collect(Collectors.toSet())
        );
        return phuongAn;
    }

}
