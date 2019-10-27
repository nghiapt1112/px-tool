package com.px.tool.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "request")
public class Request extends EntityDefault {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long requestId;

    @Column
    @Enumerated
    private RequestStatus status;

    @Column
    private Long createId;

//
//    @OneToOne
//    private KiemHong kiemHong;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "pdhId")
//    private PhieuDatHang phieuDatHang;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "paId")
//    private PhuongAn phuongAn;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "tpId")
//    private CongNhanThanhPham congNhanThanhPham;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "userId")
//    private User user;

}
