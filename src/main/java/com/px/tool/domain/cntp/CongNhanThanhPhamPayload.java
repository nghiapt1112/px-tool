package com.px.tool.domain.cntp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.px.tool.domain.user.User;
import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class CongNhanThanhPhamPayload extends AbstractObject {
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

    private Float dong;
    private Float gioX;
    private Float laoDongTienLuong;

    private String dvt;
    private String to;
    private String soLuong;

    @JsonProperty("yKienNguoiGiaoViec")
    private String yKienNguoiGiaoViec;

    @JsonProperty("yKienNguoiThucHien")
    private String yKienNguoiThucHien;

    @JsonProperty("yKienTPKCS")
    private String yKienTPKCS;

    @JsonProperty("yKienTpkcsXacNhan")
    private String yKienTpkcsXacNhan;
    private Set<NoiDungThucHienPayload> noiDungThucHiens = new HashSet<>();

    public static CongNhanThanhPhamPayload fromEntity(CongNhanThanhPham congNhanThanhPham) {
        CongNhanThanhPhamPayload congNhanThanhPhamPayload = new CongNhanThanhPhamPayload();
        BeanUtils.copyProperties(congNhanThanhPham, congNhanThanhPhamPayload);
        congNhanThanhPhamPayload.setNoiDungThucHiens(
                congNhanThanhPham.getNoiDungThucHiens()
                        .stream()
                        .map(NoiDungThucHienPayload::fromEntity)
                        .collect(Collectors.toSet())
        );
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
        nguoiGiaoViecDisable = true;
        nguoiThucHienDisable = true;
        tpkcsDisable = true;
        if (user.isNguoiLapPhieu()) {
            nguoiGiaoViecDisable = false;
        } else if (user.isNhanVienKCS()) {
            nguoiThucHienDisable = false;
        } else if (user.isTruongPhongKCS()) {
            tpkcsDisable = false;
        }
    }

    public void capNhatChuKy(User user) {
        if (user.isTruongPhongKCS()){

        }
        if (user.isNguoiLapPhieu()) {

        }
        if (user.isNhanVienKCS()) {

        }
    }
}
