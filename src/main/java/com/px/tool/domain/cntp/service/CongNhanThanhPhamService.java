package com.px.tool.domain.cntp.service;

import com.px.tool.domain.cntp.CongNhanThanhPham;
import com.px.tool.domain.cntp.CongNhanThanhPhamPayload;

import java.util.List;

public interface CongNhanThanhPhamService {

    CongNhanThanhPham taoCongNhanThanhPham(CongNhanThanhPhamPayload congNhanThanhPhamPayload);

    CongNhanThanhPham updateCongNhanThanhPham(CongNhanThanhPham congNhanThanhPham);

    CongNhanThanhPhamPayload timCongNhanThanhPham(Long userId, Long id);

    List<CongNhanThanhPham> timCongNhanThanhPhamTheoPhongBan(Long userId);
}
