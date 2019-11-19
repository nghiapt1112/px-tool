package com.px.tool.domain.phuongan;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.px.tool.domain.request.Request;
import com.px.tool.infrastructure.model.request.EntityDefault;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "phuong_an")
public class PhuongAn extends EntityDefault {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long paId;

    @Column
    private String tenNhaMay;

    @Column
    private String ngayThangNamPheDuyet;

    @Column
    private String maSo;

    @Column
    private String sanPham;

    @Column
    private String noiDung;

    @Column
    private String nguonKinhPhi;

    @Column
    private String toSo;

    @Column
    private String soTo;

    @Column
    private String PDH;

    //end
    @Column
    private String ngayThangNamTPKTHK;

    @Column
    private String truongPhongKTHK;

    @Column
    private String ngayThangNamTPKEHOACH;

    @Column
    private String truongPhongKeHoach;

    @Column
    private String ngayThangNamtpVatTu;

    @Column
    private String truongPhongVatTu;

    @Column
    private String ngayThangNamNguoiLap;

    @Column
    private String NguoiLap;

    // cac field tong cong
    @Column
    private BigDecimal tongCongDinhMucLaoDong;

    @Column
    private BigDecimal tongDMVTKho;

    @Column
    private BigDecimal tongDMVTMuaNgoai;

    @Column
    private BigDecimal tienLuong;


    @Column(name = "truongphong_kthk_xacnhan")
    private Boolean truongPhongKTHKXacNhan;

    @Column(name = "truongphong_kehoach_xacnhan")
    private Boolean truongPhongKeHoachXacNhan;

    @Column(name = "truongphong_vattu_xacnhan")
    private Boolean truongPhongVatTuXacNhan;

    @Column(name = "nguoilap_xacnhan")
    private Boolean nguoiLapXacNhan;

    @Column(name = "giamdoc_xacnhan")
    private Boolean giamDocXacNhan;

    @Column
    private String ngayThangNamGiamDoc;

    @Column
    private String yKienNguoiLap;

    @Column
    private String yKienTruongPhongKTHK;

    @Column
    private String yKienTruongPhongKeHoach;

    @Column
    private String yKienTruongPhongVatTu;

    @Column
    private String ykienTruongPhongKeHoach;

    @JsonManagedReference
    @OneToMany(mappedBy = "phuongAn", cascade = CascadeType.ALL)
    private Set<DinhMucLaoDong> dinhMucLaoDongs = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "phuongAn", cascade = CascadeType.ALL)
    private Set<DinhMucVatTu> dinhMucVatTus = new HashSet<>();

    @JsonBackReference
    @OneToOne(mappedBy = "phuongAn")
    private Request request;

    public boolean allApproved() {
        return
                Objects.nonNull(truongPhongKTHKXacNhan) && truongPhongKTHKXacNhan &&
                        Objects.nonNull(truongPhongKeHoachXacNhan) && truongPhongKeHoachXacNhan &&
                        Objects.nonNull(truongPhongVatTuXacNhan) && truongPhongVatTuXacNhan &&
                        Objects.nonNull(nguoiLapXacNhan) && nguoiLapXacNhan &&
                        Objects.nonNull(giamDocXacNhan) && giamDocXacNhan;
    }
}
