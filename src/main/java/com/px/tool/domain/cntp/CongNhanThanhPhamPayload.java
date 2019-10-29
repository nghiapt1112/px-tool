package com.px.tool.domain.cntp;

import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class CongNhanThanhPhamPayload extends AbstractObject {
    private Long tpId; // thanh pham id
    private Long requestId;

    private String tenSanPham;

    private String noiDung;

    private String soPA;

    private String donviThucHien;

    private String donviDatHang;

    private String soNghiemThuDuoc;

    private Set<NoiDungThucHienPayload> noiDungThucHiens = new HashSet<>();

    public CongNhanThanhPham toEntity() {
        CongNhanThanhPham congNhanThanhPham = new CongNhanThanhPham();
        BeanUtils.copyProperties(this, congNhanThanhPham);
        congNhanThanhPham.setNoiDungThucHiens(
                this.getNoiDungThucHiens()
                .stream()
                .map(NoiDungThucHienPayload::toEntity)
                .collect(Collectors.toSet())
        );
        return congNhanThanhPham;
    }

    public CongNhanThanhPhamPayload fromEntity(CongNhanThanhPham congNhanThanhPham) {
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
}
