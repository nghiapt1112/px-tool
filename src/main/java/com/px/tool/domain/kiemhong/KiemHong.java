package com.px.tool.domain.kiemhong;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.px.tool.infrastructure.model.request.EntityDefault;
import com.px.tool.domain.request.Request;
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
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "kiem_hong")
public class KiemHong extends EntityDefault {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "kh_id")
    public Long khId;

    @Column
    private String tenNhaMay;

    @Column
    private String phanXuong;

    @Column
    private String toSX;

    @Column
    private String tenVKTBKT; // may bay L39

    @Column
    private String nguonVao;// SCL TTNH

    @Column
    private String congDoan; // kiem hong chi tiet

    @Column
    private String soHieu; // 8843

    @Column
    private String soXX; // 8373y64

    @Column
    private String toSo;

    @Column
    private String soTo;

    @Column
    private String noiNhan;

    @Column
    private String ngayThangNamQuanDoc;

    @Column
    private String quanDoc;

    @Column
    private String ngayThangNamTroLyKT;

    @Column
    private String troLyKT;

    @Column
    private String ngayThangNamToTruong;

    @Column
    private String toTruong;

    @Column
    private String yKienGiamDoc;

    @JsonManagedReference
    @OneToMany(mappedBy = "kiemHong", cascade = CascadeType.ALL)
    private Set<KiemHongDetail> kiemHongDetails = new HashSet<>();

    @JsonBackReference
    @OneToOne(mappedBy = "kiemHong")
    private Request request;
}
