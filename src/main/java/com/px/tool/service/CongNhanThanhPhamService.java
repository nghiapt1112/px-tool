package com.px.tool.service;

import com.px.tool.model.CongNhanThanhPham;

import java.util.List;

public interface CongNhanThanhPhamService {

    CongNhanThanhPham taoCongNhanThanhPham(CongNhanThanhPham congNhanThanhPham);

    CongNhanThanhPham updateCongNhanThanhPham(CongNhanThanhPham congNhanThanhPham);

    CongNhanThanhPham timCongNhanThanhPham(Long id);

    List<CongNhanThanhPham> timCongNhanThanhPhamTheoPhongBan(Long userId);
}
