package com.px.tool.domain.request;

import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ThongKePayload extends AbstractObject {
    private Long tt;
    private String tenPhuKien;
    private String tenLinhKien;
    private String kyHieu;
    private Long SL;
    private String dangHuHong;
    private String ngayKiemHong;
    private String phuongPhapKhacPhuc;
    private String ngayChuyenPhongVatTu;
    private String soPhieuDatHang;
    private String ngayChuyenKT;
    private String soPA;
    private String ngayRaPA;
    private String ngayChuyenKH;
    private String ngayPheDuyet;
    private String ngayHoanThanh;
    private String xacNhanHoanThanh;
}
