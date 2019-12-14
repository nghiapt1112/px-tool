package com.px.tool.domain.cntp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.px.tool.domain.user.User;
import com.px.tool.infrastructure.exception.PXException;
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

import static com.px.tool.infrastructure.utils.CommonUtils.assignVal;
import static com.px.tool.infrastructure.utils.CommonUtils.getVal;

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
    private Boolean quanDocDisable = true;
    private Long quanDocId;
    private String quanDocFullName;
    private String quanDocSignImg;
    private String ykienQuanDoc;

    private Boolean tpkcsXacNhan;
    private Boolean tpkcsDisable = true;
    private Long tpkcsId;
    private String tpkcsFullName;
    private String tpkcsSignImg;
    private String ykientpkcs;

    // danh sach 5 to truong:
    private Boolean toTruong1XacNhan;
    private Boolean toTruong1Disable = true;
    private Long toTruong1Id;
    private String toTruong1SignImg;
    private String toTruong1fullName;
    private String ykienToTruong1;

    private Boolean toTruong2XacNhan;
    private Boolean toTruong2Disable = true;
    private Long toTruong2Id;
    private String toTruong2SignImg;
    private String toTruong2fullName;
    private String ykienToTruong2;

    private Boolean toTruong3XacNhan;
    private Boolean toTruong3Disable = true;
    private Long toTruong3Id;
    private String toTruong3SignImg;
    private String toTruong3fullName;
    private String ykienToTruong3;

    private Boolean toTruong4XacNhan;
    private Boolean toTruong4Disable = true;
    private Long toTruong4Id;
    private String toTruong4SignImg;
    private String toTruong4fullName;
    private String ykienToTruong4;

    private Boolean toTruong5XacNhan;
    private Boolean toTruong5Disable = true;
    private Long toTruong5Id;
    private String toTruong5SignImg;
    private String toTruong5fullName;
    private String ykienToTruong5;

    private List<Long> cusToTruongIds;
    private boolean nghiemThuDisable = true;

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
            congNhanThanhPhamPayload.soPA = "PA-" + congNhanThanhPham.getPhuongAn().getPaId();
        } catch (Exception e) {
            congNhanThanhPhamPayload.soPA = "[Trống]";
        }
        congNhanThanhPhamPayload.setCusToTruongIds(Collections.emptyList());
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
//        congNhanThanhPham.setPhanXuongThucHien(CommonUtils.toString(cusToTruongIds));

        return congNhanThanhPham;
    }

    public void filterPermission(User user) {
        if (user.isQuanDocPhanXuong()) {
            quanDocDisable = false;
        }
        if (user.isTruongPhongKCS()) {
            tpkcsDisable = false;
        }
        if (user.getUserId().equals(toTruong1Id)) {
            toTruong1Disable = false;
            nghiemThuDisable = false;
        }
        if (user.getUserId().equals(toTruong2Id)) {
            toTruong2Disable = false;
            nghiemThuDisable = false;
        }
        if (user.getUserId().equals(toTruong3Id)) {
            toTruong3Disable = false;
            nghiemThuDisable = false;
        }
        if (user.getUserId().equals(toTruong4Id)) {
            toTruong4Disable = false;
            nghiemThuDisable = false;
        }
        if (user.getUserId().equals(toTruong5Id)) {
            toTruong5Disable = false;
            nghiemThuDisable = false;
        }
    }

    public void capNhatChuKy(User user) {
        if (user.isTruongPhongKCS() && tpkcsXacNhan) {
            tpkcsId = user.getUserId();
        }
        if (user.isQuanDocPhanXuong() && quanDocXacNhan) {
            quanDocId = user.getUserId();
        }
    }

    public Boolean getTpkcsXacNhan() {
        return tpkcsXacNhan == null ? false : tpkcsXacNhan;
    }

    public Boolean getQuanDocXacNhan() {
        return quanDocXacNhan == null ? false : quanDocXacNhan;
    }

    public Boolean getToTruong1XacNhan() {
        return toTruong1XacNhan == null ? false : toTruong1XacNhan;
    }

    public Boolean getToTruong2XacNhan() {
        return toTruong2XacNhan == null ? false : toTruong2XacNhan;
    }

    public Boolean getToTruong3XacNhan() {
        return toTruong3XacNhan == null ? false : toTruong3XacNhan;
    }

    public Boolean getToTruong4XacNhan() {
        return toTruong4XacNhan == null ? false : toTruong4XacNhan;
    }

    public Boolean getToTruong5XacNhan() {
        return toTruong5XacNhan == null ? false : toTruong5XacNhan;
    }


    @Override
    public void processSignImgAndFullName(Map<Long, User> userById) {
        try {
            if (this.getTpkcsXacNhan()) {
                this.setTpkcsFullName(userById.get(this.getTpkcsId()).getFullName());
                this.setTpkcsSignImg(userById.get(this.getTpkcsId()).getSignImg());
            }
            toTruong1fullName = assignVal(userById.get(toTruong1Id), "Tổ trưởng 1");
            toTruong2fullName = assignVal(userById.get(toTruong2Id), "Tổ trưởng 2");
            toTruong3fullName = assignVal(userById.get(toTruong3Id), "Tổ trưởng 3");
            toTruong4fullName = assignVal(userById.get(toTruong4Id), "Tổ trưởng 4");
            toTruong5fullName = assignVal(userById.get(toTruong5Id), "Tổ trưởng 5");


            if (quanDocXacNhan) {
                quanDocFullName = userById.get(quanDocId).getFullName();
                quanDocSignImg = userById.get(quanDocId).getSignImg();
            }
            if (toTruong1XacNhan) {
                toTruong1SignImg = userById.get(toTruong1Id).getSignImg();
            }
            if (toTruong2XacNhan) {
                toTruong2SignImg = userById.get(toTruong2Id).getSignImg();
            }

            if (toTruong3XacNhan) {
                toTruong3SignImg = userById.get(toTruong3Id).getSignImg();
            }
            if (toTruong4XacNhan) {
                toTruong4SignImg = userById.get(toTruong4Id).getSignImg();
            }
            if (toTruong5XacNhan) {
                toTruong5SignImg = userById.get(toTruong5Id).getSignImg();
            }
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
        cntp.setNgayThangNamTPKCS(existed.getNgayThangNamTPKCS());
        cntp.setNgayThangNamQuanDoc(existed.getNgayThangNamQuanDoc());

        if (cntp.getQuanDocXacNhan() && !existed.getQuanDocXacNhan()) {
            cntp.setNgayThangNamQuanDoc(DateTimeUtils.nowAsMilliSec());
        }
        if (cntp.getTpkcsXacNhan() && !existed.getTpkcsXacNhan()) {
            cntp.setNgayThangNamTPKCS(DateTimeUtils.nowAsMilliSec());
        }
    }

    @Override
    public void validateXacNhan(User user, CongNhanThanhPham request, CongNhanThanhPham existed) {
        if (user.isQuanDocPhanXuong()) {
            if (cusToTruongIds.size() > 5) {
                throw new PXException("cntp.totruong_max5");
            }
            toTruong1Id = getVal(cusToTruongIds, 0);
            toTruong2Id = getVal(cusToTruongIds, 1);
            toTruong3Id = getVal(cusToTruongIds, 2);
            toTruong4Id = getVal(cusToTruongIds, 3);
            toTruong5Id = getVal(cusToTruongIds, 4);
        } else if (user.isToTruong()) {
            if (cusToTruongIds.size() > 1) {
                throw new PXException("cntp.kcs_max1");
            }
            tpkcsId = getVal(cusToTruongIds, 0);
            for (NoiDungThucHienPayload noiDungThucHien : noiDungThucHiens) {
                if (noiDungThucHien.isInvalidData()) {
                    throw new PXException("cntp.noi_dung_thuc_hien");
                }
            }
        } else if (user.isTruongPhongKCS()) {
            if (cusToTruongIds.size() > 1) {
                throw new PXException("cntp.phanxuong_max1");
            }
            quanDocId = getVal(cusToTruongIds, 0);
        }

        request.setToTruong1Id(toTruong1Id);
        request.setToTruong2Id(toTruong2Id);
        request.setToTruong3Id(toTruong3Id);
        request.setToTruong4Id(toTruong4Id);
        request.setToTruong5Id(toTruong5Id);
        request.setTpkcsId(tpkcsId);
        request.setQuanDocId(quanDocId);
    }

    @JsonIgnore
    public boolean allNhanVienKCSAssinged() {
        for (NoiDungThucHienPayload noiDungThucHien : this.noiDungThucHiens) {
            if (noiDungThucHien.isInvalidData()) {
                return false;
            }
        }
        return true;
    }

}
