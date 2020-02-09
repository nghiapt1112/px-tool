package com.px.tool.domain.dathang;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.px.tool.domain.RequestStatus;
import com.px.tool.domain.RequestType;
import com.px.tool.domain.kiemhong.KiemHong;
import com.px.tool.domain.kiemhong.KiemHongDetail;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.user.User;
import com.px.tool.infrastructure.exception.PXException;
import com.px.tool.infrastructure.logger.PXLogger;
import com.px.tool.infrastructure.model.payload.AbstractPayLoad;
import com.px.tool.infrastructure.utils.CommonUtils;
import com.px.tool.infrastructure.utils.DateTimeUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class PhieuDatHangPayload extends AbstractPayLoad<PhieuDatHang> {
    private Long requestId;
    private Long pdhId;
    private String tenNhaMay;
    private String tenPhong;
    private String so;
    private String donViYeuCau;
    private String phanXuong;
    private String noiDung;
    private Long noiNhan;
    private String ngayThangNamTPKTHK;
    private String TPKTHK;
    private Boolean tpkthkXacNhan;
    private String ngayThangNamTPVatTu;
    private String TPVatTu;
    private Boolean tpvatTuXacNhan;
    private String ngayThangNamNguoiDatHang;
    private String NguoiDatHang;
    private Boolean nguoiDatHangXacNhan;

    private boolean nguoiDatHangDisable;
    private boolean tpvatTuDisable;
    private boolean tpkthkDisable;


    @JsonProperty("yKienNguoiDatHang")
    private String yKienNguoiDatHang;

    @JsonProperty("yKienTPKTHK")
    private String yKienTPKTHK;

    @JsonProperty("yKienTPVatTu")
    private String yKienTPVatTu;
    private Long chuyen; // id cua user dc nhan
    private List<PhieuDatHangDetailPayload> phieuDatHangDetails = new ArrayList<>();

    // chuky + ten
    private String nguoiDatHangSignImg;
    private String tpvatTuSignImg;
    private String tpkthkSignImg;

    private String nguoiDatHangFullName;
    private String tpvatTuFullName;
    private String tpkthkFullName;

    private Long nguoiDatHangId;
    private Long tpvatTuId;
    private Long tpkthkId;

    // update flow moi
    private Long trolyKT; // cac tro ly nay thuoc 8,9 level = 4

    private List<Long> cusReceivers;
    private String cusNoiDung;

    public static PhieuDatHangPayload fromEntity(PhieuDatHang phieuDatHang) {
        PhieuDatHangPayload payload = new PhieuDatHangPayload();
        BeanUtils.copyProperties(phieuDatHang, payload);
        payload.setPhieuDatHangDetails(
                phieuDatHang.getPhieuDatHangDetails()
                        .stream()
                        .map(PhieuDatHangDetailPayload::fromEntity)
                        .map(detail -> {
                            if (!StringUtils.isEmpty(detail.getSoPhieuDatHang())) {
                                return detail;
                            }
                            if (!StringUtils.isEmpty(phieuDatHang.getSo())) {
                                detail.setSoPhieuDatHang(phieuDatHang.getSo());
                            }
                            return detail;
                        })
                        .collect(Collectors.toList())
        );
        payload.setNoiNhan(null);
        payload.ngayThangNamNguoiDatHang = DateTimeUtils.toString(phieuDatHang.getNgayThangNamNguoiDatHang());
        payload.ngayThangNamTPKTHK = DateTimeUtils.toString(phieuDatHang.getNgayThangNamTPKTHK());
        payload.ngayThangNamTPVatTu = DateTimeUtils.toString(phieuDatHang.getNgayThangNamTPVatTu());
        payload.setCusReceivers(CommonUtils.toCollection(phieuDatHang.getCusReceivers()));
        return payload;
    }

    public PhieuDatHang toEntity(PhieuDatHang phieuDatHang) {
        if (pdhId != null && pdhId <= 0) {
            pdhId = null;
        }
        BeanUtils.copyProperties(this, phieuDatHang);
        phieuDatHang.setPhieuDatHangDetails(
                phieuDatHangDetails.stream()
                        .map(payload -> {
                            PhieuDatHangDetail entity = payload.toEntity();
                            if (Objects.nonNull(phieuDatHang.getPdhId())) {
                                entity.setPhieuDatHang(phieuDatHang);
                            }
                            if (!StringUtils.isEmpty(so)) {
                                entity.setSoPhieuDatHang(so);
                            }
                            return entity;
                        })
                        .collect(Collectors.toSet())
        );
        phieuDatHang.setCusReceivers(CommonUtils.toString(this.getCusReceivers()));
        return phieuDatHang;
    }

    public boolean notIncludeId() {
        return pdhId != null && pdhId <= 0;
    }

    public PhieuDatHangPayload filterPermission(User currentUser) {
        nguoiDatHangDisable = true;
        tpkthkDisable = true;
        tpvatTuDisable = true;
        if (currentUser.isNhanVienVatTu()) {
            nguoiDatHangDisable = false;
        } else if (currentUser.isTruongPhongVatTu()) {
            tpvatTuDisable = false;
        } else if (currentUser.isTruongPhongKTHK()) {
            tpkthkDisable = false;
        }
        return this;
    }

    public void capNhatChuKy(User user) {
        if (user.isNhanVienVatTu() && nguoiDatHangXacNhan) {
            this.nguoiDatHangId = user.getUserId();
        }
        if (user.isTruongPhongVatTu() && tpvatTuXacNhan) {
            tpvatTuId = user.getUserId();
            trolyKT = noiNhan;
        }
        if (user.isTruongPhongKTHK() && tpkthkXacNhan) {
            tpkthkId = user.getUserId();
        }
    }

    public Boolean getTpkthkXacNhan() {
        return tpkthkXacNhan == null ? false : tpkthkXacNhan;
    }

    public Boolean getTpvatTuXacNhan() {
        return tpvatTuXacNhan == null ? false : tpvatTuXacNhan;
    }

    public Boolean getNguoiDatHangXacNhan() {
        return nguoiDatHangXacNhan == null ? false : nguoiDatHangXacNhan;
    }

    @Override
    public void processSignImgAndFullName(Map<Long, User> userById) {
        try {
            if (this.getNguoiDatHangXacNhan()) {
                this.setNguoiDatHangFullName(userById.get(this.getNguoiDatHangId()).getFullName());
                this.setNguoiDatHangSignImg(userById.get(this.getNguoiDatHangId()).getSignImg());
            }
            if (this.getTpkthkXacNhan()) {
                this.setTpkthkFullName(userById.get(this.getTpkthkId()).getFullName());
                this.setTpkthkSignImg(userById.get(this.getTpkthkId()).getSignImg());
            }
            if (this.getTpvatTuXacNhan()) {
                this.setTpvatTuFullName(userById.get(this.getTpvatTuId()).getFullName());
                this.setTpvatTuSignImg(userById.get(this.getTpvatTuId()).getSignImg());
            }
        } catch (Exception e) {
            PXLogger.error("[DAT_HANG] Parse chữ ký và full name bị lỗi.");
        }
    }

    @Override
    public Collection<Long> getDeletedIds(PhieuDatHang o) {
        if (Objects.isNull(o)) {
            return Collections.emptyList();
        }

        Set<Long> requestIds = this.getPhieuDatHangDetails()
                .stream()
                .map(el -> el.getPdhDetailId())
                .collect(Collectors.toSet());

        return o.getPhieuDatHangDetails()
                .stream()
                .map(detail -> detail.getPdhDetailId())
                .filter(el -> !requestIds.contains(el))
                .collect(Collectors.toSet());
    }

    @Override
    public void capNhatNgayThangChuKy(PhieuDatHang requestDatHang, PhieuDatHang existedPhieuDatHang) {
        requestDatHang.setNgayThangNamNguoiDatHang(existedPhieuDatHang.getNgayThangNamNguoiDatHang());
        requestDatHang.setNgayThangNamTPVatTu(existedPhieuDatHang.getNgayThangNamTPVatTu());
        requestDatHang.setNgayThangNamTPKTHK(existedPhieuDatHang.getNgayThangNamTPKTHK());
        // cac field khac
//        requestDatHang.setNguoiDatHangId(existedPhieuDatHang.getNguoiDatHangId());
//        requestDatHang.setTpvatTuId(existedPhieuDatHang.getTpvatTuId());
//        requestDatHang.setTpkthkId(existedPhieuDatHang.getTpkthkId());


        if (requestDatHang.getNguoiDatHangXacNhan() != existedPhieuDatHang.getNguoiDatHangXacNhan()) {
            requestDatHang.setNgayThangNamNguoiDatHang(DateTimeUtils.nowAsMilliSec());
        }
        if (requestDatHang.getTpvatTuXacNhan() != existedPhieuDatHang.getTpvatTuXacNhan()) {
            requestDatHang.setNgayThangNamTPVatTu(DateTimeUtils.nowAsMilliSec());
        }
        if (requestDatHang.getTpkthkXacNhan() != existedPhieuDatHang.getTpkthkXacNhan()) {
            requestDatHang.setNgayThangNamTPKTHK(DateTimeUtils.nowAsMilliSec());
        }
    }

    @Override
    public void validateXacNhan(User user, PhieuDatHang request, PhieuDatHang existed) {
        if (user.isTroLyKT() && (CollectionUtils.isEmpty(cusReceivers))) {
            throw new PXException("dathang.noi_nhan.empty");
        }
        if (user.isTruongPhongVatTu()){
            if (!tpvatTuXacNhan && StringUtils.isEmpty(yKienTPVatTu) && Objects.nonNull(noiNhan)) {
                throw new PXException("dathang.tpvattu_xacnhan.ykien");
            }
        }
        if (user.isTruongPhongKTHK()){
            if(!tpkthkXacNhan && StringUtils.isEmpty(yKienTPKTHK) && Objects.nonNull(noiNhan)) {
                throw new PXException("dathang.tpkthk_xacnhan.ykien");
            }
        }
        // clear noi nhan, vi day la step cuoi cung, ko can phai co noi nhan
        if (user.isTruongPhongKTHK()) {
            noiNhan = null;
        }
    }

    public Request toRequestEntity() {
        Request request = new Request();
        request.setStatus(RequestType.DAT_HANG);
        // TODO: kiem tra xem co miss field gi khong
        return request;
    }

    public KiemHong toKiemHongEntity() {
        KiemHong kiemHong = new KiemHong();
//        kiemHong.setKhId();
        kiemHong.setTenNhaMay(tenNhaMay);
//        kiemHong.setPhanXuong(phanXuong);
//        kiemHong.setToSX();
//        kiemHong.setTenVKTBKT();
//        kiemHong.setNguonVao();
//        kiemHong.setCongDoan();
//        kiemHong.setSoHieu();
//        kiemHong.setSoXX();
//        kiemHong.setToSo();
//        kiemHong.setSoTo();
        kiemHong.setNoiNhan(noiNhan);
//        kiemHong.setNgayThangNamQuanDoc();
//        kiemHong.setQuanDocXacNhan();
//        kiemHong.setQuanDocXacNhan();
//        kiemHong.setNgayThangNamTroLyKT();
//        kiemHong.setTroLyKTXacNhan();
//        kiemHong.setToTruong();
//        kiemHong.setGiamDocXacNhan();
//        kiemHong.setYKienGiamDoc();
//        kiemHong.setYKienQuanDoc();
//        kiemHong.setYKienToTruong();
//        kiemHong.setYKienTroLyKT();
//        kiemHong.setQuanDocId();
//        kiemHong.setTroLyId();
//        kiemHong.setToTruongId();
//        kiemHong.setCusReceivers(cusReceivers);
        kiemHong.setCusNoiDung(noiDung);
//        kiemHong.setKiemHongDetails();
        List<KiemHongDetail> kiemHongDetails = new ArrayList<>();
        for (PhieuDatHangDetailPayload phieuDatHangDetail : this.phieuDatHangDetails) {
            kiemHongDetails.add(phieuDatHangDetail.toKiemHongDetailEntity());
        }
        return kiemHong;
    }

}
