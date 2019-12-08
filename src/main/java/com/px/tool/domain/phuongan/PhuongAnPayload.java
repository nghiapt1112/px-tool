package com.px.tool.domain.phuongan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.px.tool.domain.user.User;
import com.px.tool.infrastructure.logger.PXLogger;
import com.px.tool.infrastructure.model.payload.AbstractPayLoad;
import com.px.tool.infrastructure.utils.DateTimeUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public class PhuongAnPayload extends AbstractPayLoad<PhuongAn> {
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

    private String ngayThangNamGiamDoc;
    private Boolean giamDocXacNhan;
    private Boolean truongPhongKTHKXacNhan;
    private Boolean truongPhongKeHoachXacNhan;
    private Boolean truongPhongVatTuXacNhan;
    private Boolean nguoiLapXacNhan;

    private boolean truongPhongKTHKDisable;
    private boolean truongPhongKeHoachDisable;
    private boolean truongPhongVatTuDisable;
    private boolean nguoiLapDisable;
    private boolean giamDocDisable;

    @JsonProperty("yKienNguoiLap")
    private String yKienNguoiLap;

    @JsonProperty("yKienTruongPhongKTHK")
    private String yKienTruongPhongKTHK;

    @JsonProperty("yKienTruongPhongKeHoach")
    private String yKienTruongPhongKeHoach;

    @JsonProperty("yKienTruongPhongVatTu")
    private String yKienTruongPhongVatTu;

    private List<String> files;
    // chu ky + ten
    private String truongPhongKTHKSignImg;
    private String truongPhongKeHoachSignImg;
    private String truongPhongVatTuSignImg;
    private String nguoiLapSignImg;
    private String giamDocSignImg;

    private String truongPhongKTHKFullName;
    private String truongPhongKeHoachFullName;
    private String truongPhongVatTuFullName;
    private String nguoiLapFullName;
    private String giamDocFullName;

    private Long truongPhongKTHKId;
    private Long truongPhongKeHoachId;
    private Long truongPhongVatTuId;
    private Long nguoiLapId;
    private Long giamDocId;

    private List<Long> cusReceivers;
    private String cusNoiDung;
    private List<Long> nguoiThucHien;


    public static PhuongAnPayload fromEntity(PhuongAn phuongAn) {
        PhuongAnPayload payload = new PhuongAnPayload();
        BeanUtils.copyProperties(phuongAn, payload);
        payload.dinhMucLaoDongs = phuongAn.getDinhMucLaoDongs().stream()
                .map(DinhMucLaoDongPayload::fromEntity)
                .sorted(Comparator.comparingLong(DinhMucLaoDongPayload::getDmId))
                .collect(Collectors.toCollection(LinkedList::new));
        payload.dinhMucVatTus = phuongAn.getDinhMucVatTus().stream()
                .map(DinhMucVatTuPayload::fromEntity)
                .sorted(Comparator.comparingLong(DinhMucVatTuPayload::getVtId))
                .collect(Collectors.toCollection(LinkedList::new));
//        phuongAnPayload.files = Arrays.asList("imgpsh_fullsize.jpeg", "1111111111111111ok.jpg");
        payload.setNoiNhan(null);

        try {
            List<Long> recv = new ArrayList<>();
            for (String s : phuongAn.getCusReceivers().split(",")) {
                recv.add(Long.valueOf(s));
            }
            payload.setCusReceivers(recv);
        } catch (Exception e) {

        }
//        payload.ngayThangNamGiamDoc = DateTimeUtils.toString(phuongAn.getngaythangnam);
//        payload.ngayThangNamNguoiLap = "";
//        payload.ngayThangNamPheDuyet = "";
//        payload.ngayThangNamTPKEHOACH = "";
//        payload.ngayThangNamTPKTHK = "";
//        payload.ngayThangNamtpVatTu = "";

        return payload;
    }

    public PhuongAnPayload withFiles(List<String> files) {
        this.files = files;
        return this;
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
        try {
            String s = "";
            for (Long cusReceiver : this.cusReceivers) {
                s += cusReceiver + ",";
            }
            s = s.substring(0, s.length() - 1);
            phuongAn.setCusReceivers(s);
        } catch (Exception e) {

        }
        return phuongAn;
    }

    public PhuongAnPayload filterPermission(User user) {
        truongPhongKTHKDisable = true;
        truongPhongKeHoachDisable = true;
        truongPhongVatTuDisable = true;
        nguoiLapDisable = true;
        giamDocDisable = true;
        if (user.isTruongPhongKTHK()) {
            truongPhongKTHKDisable = false;
        } else if (user.isTruongPhongKeHoach()) {
            truongPhongKeHoachDisable = false;
        } else if (user.isTruongPhongVatTu()) {
            truongPhongVatTuDisable = false;
        } else if (user.isNguoiLapPhieu()) {
            nguoiLapDisable = false;
        } else if (user.getLevel() == 2) {
            giamDocDisable = false;
        }
        return this;
    }

    public void capNhatChuKy(User user) {
        if (user.isNguoiLapPhieu() && nguoiLapXacNhan) {
            nguoiLapId = user.getUserId();
        }
        if (user.isTruongPhongVatTu() && truongPhongVatTuXacNhan) {
            truongPhongVatTuId = user.getUserId();
        }
        if (user.isTruongPhongKeHoach() && truongPhongKeHoachXacNhan) {
            truongPhongKeHoachId = user.getUserId();
        }
        if (user.isTruongPhongKTHK() && truongPhongKTHKXacNhan) {
            truongPhongKTHKId = user.getUserId();
        }
        if (user.getLevel() == 3 && giamDocXacNhan) {
            giamDocId = user.getUserId();
        }
    }

    public Boolean getGiamDocXacNhan() {
        return giamDocXacNhan == null ? false : giamDocXacNhan;
    }

    public Boolean getTruongPhongKTHKXacNhan() {
        return truongPhongKTHKXacNhan == null ? false : truongPhongKTHKXacNhan;
    }

    public Boolean getTruongPhongKeHoachXacNhan() {
        return truongPhongKeHoachXacNhan == null ? false : truongPhongKeHoachXacNhan;
    }

    public Boolean getTruongPhongVatTuXacNhan() {
        return truongPhongVatTuXacNhan == null ? false : truongPhongVatTuXacNhan;
    }

    public Boolean getNguoiLapXacNhan() {
        return nguoiLapXacNhan == null ? false : nguoiLapXacNhan;
    }

    @Override
    public void processSignImgAndFullName(Map<Long, User> userById) {
        try {
            if (this.getGiamDocXacNhan()) {
                this.setGiamDocSignImg(userById.get(this.getGiamDocId()).getSignImg());
                this.setGiamDocFullName(userById.get(this.getGiamDocId()).getFullName());
            }
            if (this.getTruongPhongKTHKXacNhan()) {
                this.setTruongPhongKTHKSignImg(userById.get(this.getTruongPhongKTHKId()).getSignImg());
                this.setTruongPhongKTHKFullName(userById.get(this.getTruongPhongKTHKId()).getFullName());
            }
            if (this.getTruongPhongKeHoachXacNhan()) {
                this.setTruongPhongKeHoachSignImg(userById.get(this.getTruongPhongKeHoachId()).getSignImg());
                this.setTruongPhongKeHoachFullName(userById.get(this.getTruongPhongKeHoachId()).getFullName());
            }
            if (this.getTruongPhongVatTuXacNhan()) {
                this.setTruongPhongVatTuSignImg(userById.get(this.getTruongPhongVatTuId()).getSignImg());
                this.setTruongPhongVatTuFullName(userById.get(this.getTruongPhongVatTuId()).getFullName());
            }
            if (this.getNguoiLapXacNhan()) {
                this.setNguoiLapSignImg(userById.get(this.getNguoiLapId()).getSignImg());
                this.setNguoiLapFullName(userById.get(this.getNguoiLapId()).getFullName());
            }
        } catch (Exception e) {
            PXLogger.error("[PHUONG_AN] Parse chữ ký và full name bị lỗi.");
        }

    }

    @Override
    public Collection<Long> getDeletedIds(PhuongAn o) {
        return null;
    }

    @Override
    public void capNhatNgayThangChuKy(PhuongAn pa, PhuongAn existedPhuongAn) {
        if (pa.getTruongPhongVatTuXacNhan() != existedPhuongAn.getTruongPhongVatTuXacNhan()) {
            pa.setNgayThangNamtpVatTu(DateTimeUtils.nowAsMilliSec());
        }
        if (pa.getTruongPhongKeHoachXacNhan() != existedPhuongAn.getTruongPhongKeHoachXacNhan()) {
            pa.setNgayThangNamTPKEHOACH(DateTimeUtils.nowAsString());
        }
        if (pa.getNguoiLapXacNhan() != existedPhuongAn.getNguoiLapXacNhan()) {
            pa.setNgayThangNamNguoiLap(DateTimeUtils.nowAsMilliSec());
        }
        if (pa.getTruongPhongKTHKXacNhan() != existedPhuongAn.getTruongPhongKTHKXacNhan()) {
            pa.setNgayThangNamTPKTHK(DateTimeUtils.nowAsMilliSec());
        }
        if (pa.getGiamDocXacNhan() != existedPhuongAn.getGiamDocXacNhan()) {
            pa.setNgayThangNamGiamDoc(DateTimeUtils.nowAsMilliSec());
        }
    }
}
