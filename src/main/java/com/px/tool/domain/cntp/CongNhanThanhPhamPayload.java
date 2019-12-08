package com.px.tool.domain.cntp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.px.tool.domain.user.User;
import com.px.tool.infrastructure.logger.PXLogger;
import com.px.tool.infrastructure.model.payload.AbstractPayLoad;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public class CongNhanThanhPhamPayload extends AbstractPayLoad {
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

    private Boolean nguoiGiaoViecXacNhan;
    private Boolean nguoiThucHienXacNhan;
    private Boolean tpkcsXacNhan;

    private boolean nguoiGiaoViecDisable;
    private boolean nguoiThucHienDisable;
    private boolean tpkcsDisable;

    private String ngayThangNamNguoiThucHien;
    private String ngayThangNamTPKCS;

    private Float dong;
    private Float gioX;
    private Float laoDongTienLuong;

    private String dvt;
    private String to;
    private String soLuong;
    private Long noiNhan;

    @JsonProperty("yKienNguoiGiaoViec")
    private String yKienNguoiGiaoViec;

    @JsonProperty("yKienNguoiThucHien")
    private String yKienNguoiThucHien;

    @JsonProperty("yKienTPKCS")
    private String yKienTPKCS;

    @JsonProperty("yKienTpkcsXacNhan")
    private String yKienTpkcsXacNhan;
    private List<NoiDungThucHienPayload> noiDungThucHiens = new ArrayList<>();

    // chu ky + full name
    private String nguoiGiaoViecFullName;
    private String nguoiThucHienFullName;
    private String tpkcsFullName;

    private String nguoiGiaoViecSignImg;
    private String nguoiThucHienSignImg;
    private String tpkcsSignImg;

    private Long nguoiGiaoViecId;
    private Long nguoiThucHienId;
    private Long tpkcsId;

    public static CongNhanThanhPhamPayload fromEntity(CongNhanThanhPham congNhanThanhPham) {
        CongNhanThanhPhamPayload congNhanThanhPhamPayload = new CongNhanThanhPhamPayload();
        BeanUtils.copyProperties(congNhanThanhPham, congNhanThanhPhamPayload);
        congNhanThanhPhamPayload.setNoiDungThucHiens(
                congNhanThanhPham.getNoiDungThucHiens()
                        .stream()
                        .map(NoiDungThucHienPayload::fromEntity)
                        .collect(Collectors.toList())
        );
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
        nguoiGiaoViecDisable = true; // field nay bo
        nguoiThucHienDisable = true; // field nay cho len tren, vi no co nhieu nguoi thuc hien
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
        if (user.isNguoiLapPhieu() && nguoiThucHienXacNhan) {
            nguoiThucHienId = user.getUserId();
        }
        if (user.isNhanVienKCS() && nguoiGiaoViecXacNhan) {
            nguoiGiaoViecId = user.getUserId();
        }
    }

    public Boolean getNguoiGiaoViecXacNhan() {
        return nguoiGiaoViecXacNhan == null ? false : nguoiGiaoViecXacNhan;
    }

    public Boolean getNguoiThucHienXacNhan() {
        return nguoiThucHienXacNhan == null ? false : nguoiThucHienXacNhan;
    }

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
            if (this.getNguoiThucHienXacNhan()) {
                this.setNguoiThucHienFullName(userById.get(this.getNguoiThucHienId()).getFullName());
                this.setNguoiThucHienSignImg(userById.get(this.getNguoiThucHienId()).getSignImg());
            }
            if (this.getNguoiGiaoViecXacNhan()) {
                this.setNguoiGiaoViecFullName(userById.get(this.getNguoiGiaoViecId()).getFullName());
                this.setNguoiGiaoViecSignImg(userById.get(this.getNguoiGiaoViecId()).getSignImg());
            }
        } catch (Exception e) {
            PXLogger.error("[CNTP] Parse chữ ký và full name bị lỗi.");
        }
    }
}
