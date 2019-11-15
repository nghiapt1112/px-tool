package com.px.tool.domain.phuongan;

import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
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
    private List<DinhMucLaoDongPayload> dinhMucLaoDongs = new LinkedList<>();
    private List<DinhMucVatTuPayload> dinhMucVatTus = new LinkedList<>();
    private Long noiNhan; // id cua user dc nhan


    private Boolean giamDocXacNhan;
    private Boolean truongPhongKTHKXacNhan;
    private Boolean truongPhongKeHoachXacNhan;
    private Boolean truongPhongVatTuXacNhan;
    private Boolean nguoiLapXacNhan;
    private List<String> files ;

    public static PhuongAnPayload fromEntity(PhuongAn phuongAn) {
        PhuongAnPayload phuongAnPayload = new PhuongAnPayload();
        BeanUtils.copyProperties(phuongAn, phuongAnPayload);
        phuongAnPayload.dinhMucLaoDongs = phuongAn.getDinhMucLaoDongs().stream()
                .map(DinhMucLaoDongPayload::fromEntity)
                .sorted(Comparator.comparingLong(DinhMucLaoDongPayload::getDmId))
                .collect(Collectors.toCollection(LinkedList::new));
        phuongAnPayload.dinhMucVatTus = phuongAn.getDinhMucVatTus().stream()
                .map(DinhMucVatTuPayload::fromEntity)
                .sorted(Comparator.comparingLong(DinhMucVatTuPayload::getVtId))
                .collect(Collectors.toCollection(LinkedList::new));
        phuongAnPayload.files = Arrays.asList("imgpsh_fullsize.jpeg", "1111111111111111ok.jpg");
        return phuongAnPayload;
    }

    public PhuongAn toEntity(PhuongAn phuongAn) {
        if (paId != null && paId <= 0) {
            paId = null;
        }
        BeanUtils.copyProperties(this, phuongAn);
        phuongAn.setDinhMucLaoDongs(
                dinhMucLaoDongs
                        .stream()
                        .map(payload -> {
                            DinhMucLaoDong entity = payload.toEntity();
                            if (Objects.nonNull(phuongAn.getPaId())) {
                                entity.setPhuongAn(phuongAn);
                            }
                            return entity;
                        })
                        .collect(Collectors.toSet())
        );
        phuongAn.setDinhMucVatTus(
                dinhMucVatTus.stream()
                        .map(payload -> {
                            DinhMucVatTu entity = payload.toEntity();
                            if (Objects.nonNull(phuongAn.getPaId())) {
                                entity.setPhuongAn(phuongAn);
                            }
                            return entity;
                        })
                        .collect(Collectors.toSet())
        );
        return phuongAn;
    }

}
