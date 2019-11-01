package com.px.tool.domain.dathang.service;

import com.px.tool.domain.dathang.PhieuDatHang;
import com.px.tool.domain.dathang.PhieuDatHangPayload;

import java.util.Collection;
import java.util.List;

public interface PhieuDatHangService {
    PhieuDatHangPayload findById(Long id);

    List<PhieuDatHang> findByPhongBan(Long userId);

    PhieuDatHang save(PhieuDatHangPayload phieuDatHangPayload);

    PhieuDatHang save(PhieuDatHang phieuDatHang);

}
