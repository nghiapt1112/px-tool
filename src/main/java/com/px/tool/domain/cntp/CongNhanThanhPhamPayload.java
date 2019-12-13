package com.px.tool.domain.cntp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.px.tool.domain.user.User;
import com.px.tool.infrastructure.logger.PXLogger;
import com.px.tool.infrastructure.model.payload.AbstractPayLoad;
import com.px.tool.infrastructure.utils.DateTimeUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

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
public class CongNhanThanhPhamPayload extends AbstractPayLoad<CongNhanThanhPham> {
    private Long tpId; // thanh pham id
    private Long requestId;

    private String tenSanPham;

    private String noiDung;

    @JsonProperty("soPa")
    private String soPA;

    @JsonProperty("donViThucHien")
    private String donviThucHien;

    @JsonProperty("donViDatHang")
    private String donviDatHang;

    private String soNghiemThuDuoc;
    private Float dong;
    private Float gioX;
    private Float laoDongTienLuong;

    private String dvt;
    private String to;
    private String soLuong;
    private Long noiNhan;

    private List<NoiDungThucHienPayload> noiDungThucHiens = new ArrayList<>();

    // chu ky + full name
    private Boolean quanDocXacNhan;
    private Boolean quanDocDisable;
    private Long quanDocId;
    private String quanDocFullName;
    private String quanDocSignImg;
    private String ykienQuanDoc;

    private Boolean tpkcsXacNhan;
    private Boolean tpkcsDisable;
    private Long tpkcsId;
    private String tpkcsFullName;
    private String tpkcsSignImg;
    private String ykientpkcs;


    // danh sach 5 to truong:
    private Boolean toTruong1XacNhan;
    private Boolean toTruong1Disable;
    private Long toTruong1Id;
    private String toTruong1SignImg;
    private String toTruong1fullName;
    private String ykienToTruong1;

    private Boolean toTruong2XacNhan;
    private Boolean toTruong2Disable;
    private Long toTruong2Id;
    private String toTruong2SignImg;
    private String toTruong2fullName;
    private String ykienToTruong2;

    private Boolean toTruong3XacNhan;
    private Boolean toTruong3Disable;
    private Long toTruong3Id;
    private String toTruong3SignImg;
    private String toTruong3fullName;
    private String ykienToTruong3;

    private Boolean toTruong4XacNhan;
    private Boolean toTruong4Disable;
    private Long toTruong4Id;
    private String toTruong4SignImg;
    private String toTruong4fullName;
    private String ykienToTruong4;

    private Boolean toTruong5XacNhan;
    private Boolean toTruong5Disable;
    private Long toTruong5Id;
    private String toTruong5SignImg;
    private String toTruong5fullName;
    private String ykienToTruong5;

    private List<Long> cusToTruongIds;

    public static CongNhanThanhPhamPayload fromEntity(CongNhanThanhPham congNhanThanhPham) {
        CongNhanThanhPhamPayload congNhanThanhPhamPayload = new CongNhanThanhPhamPayload();
        BeanUtils.copyProperties(congNhanThanhPham, congNhanThanhPhamPayload);
        congNhanThanhPhamPayload.setNoiDungThucHiens(
                congNhanThanhPham.getNoiDungThucHiens()
                        .stream()
                        .map(NoiDungThucHienPayload::fromEntity)
                        .collect(Collectors.toList())
        );
        try {
            congNhanThanhPhamPayload.soPA = "PA-"+congNhanThanhPham.getPhuongAn().getPaId();
        } catch (Exception e) {
            congNhanThanhPhamPayload.soPA = "[Trống]";
        }
        congNhanThanhPhamPayload.setNoiNhan(null);
        return congNhanThanhPhamPayload;
    }

    public CongNhanThanhPham toEntity(CongNhanThanhPham congNhanThanhPham) {
        if (tpId != null && tpId <= 0) {
            tpId = null;
        }
        BeanUtils.copyProperties(this, congNhanThanhPham);
        congNhanThanhPham.setNoiDungThucHiens(
                this.getNoiDungThucHiens()
                        .stream()
                        .map(detail -> {
                            NoiDungThucHien entity = detail.toEntity();
                            if (Objects.nonNull(congNhanThanhPham.getTpId())) {
                                entity.setCongNhanThanhPham(congNhanThanhPham);
                            }
                            return entity;
                        })
                        .collect(Collectors.toSet())
        );
        return congNhanThanhPham;
    }

    public void filterPermission(User user) {
//        nguoiGiaoViecDisable = true; // field nay bo
//        nguoiThucHienDisable = true; // field nay cho len tren, vi no co nhieu nguoi thuc hien
        tpkcsDisable = true;
//        if (user.isNguoiLapPhieu()) {
//            nguoiGiaoViecDisable = false;
//        } else if (user.isNhanVienKCS()) {
//            nguoiThucHienDisable = false;
//        } else if (user.isTruongPhongKCS()) {
//            tpkcsDisable = false;
//        }

        if (user.isTruongPhongKCS()) {
            tpkcsDisable = false;
        }
    }

    public void capNhatChuKy(User user) {
        if (user.isTruongPhongKCS() && tpkcsXacNhan) {
            tpkcsId = user.getUserId();
        }
//        if (user.isNguoiLapPhieu() && nguoiThucHienXacNhan) {
//            nguoiThucHienId = user.getUserId();
//        }
//        if (user.isNhanVienKCS() && nguoiGiaoViecXacNhan) {
//            nguoiGiaoViecId = user.getUserId();
//        }
    }

//    public Boolean getNguoiGiaoViecXacNhan() {
//        return nguoiGiaoViecXacNhan == null ? false : nguoiGiaoViecXacNhan;
//    }
//
//    public Boolean getNguoiThucHienXacNhan() {
//        return nguoiThucHienXacNhan == null ? false : nguoiThucHienXacNhan;
//    }

    public Boolean getTpkcsXacNhan() {
        return tpkcsXacNhan == null ? false : tpkcsXacNhan;
    }

    @Override
    public void processSignImgAndFullName(Map<Long, User> userById) {
        try {
            if (this.getTpkcsXacNhan()) {
                this.setTpkcsFullName(userById.get(this.getTpkcsId()).getFullName());
                this.setTpkcsSignImg(userById.get(this.getTpkcsId()).getSignImg());
            }
//            if (this.getNguoiThucHienXacNhan()) {
//                this.setNguoiThucHienFullName(userById.get(this.getNguoiThucHienId()).getFullName());
//                this.setNguoiThucHienSignImg(userById.get(this.getNguoiThucHienId()).getSignImg());
//            }
//            if (this.getNguoiGiaoViecXacNhan()) {
//                this.setNguoiGiaoViecFullName(userById.get(this.getNguoiGiaoViecId()).getFullName());
//                this.setNguoiGiaoViecSignImg(userById.get(this.getNguoiGiaoViecId()).getSignImg());
//            }
        } catch (Exception e) {
            PXLogger.error("[CNTP] Parse chữ ký và full name bị lỗi.");
        }
    }

    @Override
    public Collection<Long> getDeletedIds(CongNhanThanhPham o) {
        if (Objects.isNull(o)) {
            return Collections.emptyList();
        }
        Set<Long> requestIds = this.getNoiDungThucHiens()
                .stream()
                .map(el -> el.getNoiDungId())
                .collect(Collectors.toSet());

        return o.getNoiDungThucHiens()
                .stream()
                .map(el -> el.getNoiDungId())
                .filter(el -> !requestIds.contains(el))
                .collect(Collectors.toSet());
    }

    @Override
    public void capNhatNgayThangChuKy(CongNhanThanhPham cntp, CongNhanThanhPham existed) {
        if (cntp.getNguoiThucHienXacNhan() && !existed.getNguoiThucHienXacNhan()) {
            cntp.setNgayThangNamNguoiThucHien(DateTimeUtils.nowAsMilliSec());
        }
        if (cntp.getTpkcsXacNhan() && !existed.getTpkcsXacNhan()) {
            cntp.setNgayThangNamTPKCS(DateTimeUtils.nowAsMilliSec());
        }
    }

    @Override
    public void validateXacNhan(User user, CongNhanThanhPham request, CongNhanThanhPham existed) {

    }
}
