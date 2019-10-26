package com.px.tool.service;

import com.px.tool.model.PhieuDatHang;

import java.util.List;

public interface PhieuDatHangService {
    PhieuDatHang findById(Long id);

    List<PhieuDatHang> findByPhongBan(Long userId);

    PhieuDatHang create(PhieuDatHang phieuDatHang);

    PhieuDatHang save(PhieuDatHang phieuDatHang);

}
