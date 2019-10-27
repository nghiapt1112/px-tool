package com.px.tool.model.response;

import com.px.tool.model.AbstractObject;
import com.px.tool.model.DinhMucLaoDong;
import com.px.tool.model.DinhMucVatTu;
import com.px.tool.model.PhuongAn;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class PhuongAnPayload extends AbstractObject {
    public Long paId;
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
    private Set<DinhMucLaoDong> dinhMucLaoDongs = new HashSet<>();
    private Set<DinhMucVatTu> dinhMucVatTus = new HashSet<>();

    public void toEntity(PhuongAn phuongAn) {
        BeanUtils.copyProperties(this, phuongAn);
    }
}
