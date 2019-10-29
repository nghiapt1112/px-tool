package com.px.tool.domain.cntp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.px.tool.infrastructure.model.request.AbstractObject;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "noi_dung_thuc_hien")
@Getter
@Setter
public class NoiDungThucHien extends AbstractObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long noiDungId;

    @Column
    private String noiDung;

    @Column
    private String ketQua;

    @Column
    private String nguoiLam;

    @Column
    private String nghiemThu;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "tpId", insertable = false, updatable = false)
    private CongNhanThanhPham congNhanThanhPham;
}