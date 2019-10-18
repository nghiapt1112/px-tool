package com.px.tool.model;

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

@Getter
@Setter
@Entity
@Table(name = "dinh_muc_lao_dong")
public class DinhMucLaoDong {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long dmId;

    @Column
    private String tt;

    @Column
    private String noiDungCongViec;

    @Column
    private String bacCV;

    @Column
    private String dm;

    @Column
    private String ghiChu;

    @ManyToOne
    @JoinColumn(name = "paId", insertable = false, updatable = false)
    private PhuongAn phuongAn;
}
