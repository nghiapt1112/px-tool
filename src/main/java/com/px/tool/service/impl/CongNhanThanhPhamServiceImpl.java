package com.px.tool.service.impl;

import com.px.tool.model.CongNhanThanhPham;
import com.px.tool.repository.CongNhanThanhPhamRepository;
import com.px.tool.service.CongNhanThanhPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CongNhanThanhPhamServiceImpl implements CongNhanThanhPhamService {
    @Autowired
    private CongNhanThanhPhamRepository congNhanThanhPhamRepository;

    @Override
    public CongNhanThanhPham taoCongNhanThanhPham(CongNhanThanhPham congNhanThanhPham) {
        return congNhanThanhPhamRepository.save(congNhanThanhPham);
    }

    @Override
    public CongNhanThanhPham updateCongNhanThanhPham(CongNhanThanhPham congNhanThanhPham) {
        return congNhanThanhPhamRepository.save(congNhanThanhPham);
    }

    @Override
    public CongNhanThanhPham timCongNhanThanhPham(Long id) {
        return congNhanThanhPhamRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Khong tim thay cong nhan thanh pham voi id: " + id));
    }

    @Override
    public List<CongNhanThanhPham> timCongNhanThanhPhamTheoPhongBan(Long userId) {
        return Collections.emptyList();
    }
}
