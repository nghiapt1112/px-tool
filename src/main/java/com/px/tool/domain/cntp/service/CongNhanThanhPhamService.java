package com.px.tool.domain.cntp.service;

import com.px.tool.domain.cntp.CongNhanThanhPham;
import com.px.tool.domain.cntp.CongNhanThanhPhamPayload;

import java.util.List;

public interface CongNhanThanhPhamService {

    CongNhanThanhPham saveCongNhanThanhPham(Long userId, CongNhanThanhPhamPayload congNhanThanhPhamPayload);

    CongNhanThanhPhamPayload timCongNhanThanhPham(Long userId, Long id);

    List<CongNhanThanhPham> timCongNhanThanhPhamTheoPhongBan(Long userId);
}
