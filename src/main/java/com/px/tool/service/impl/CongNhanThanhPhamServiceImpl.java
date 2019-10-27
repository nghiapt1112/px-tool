package com.px.tool.service.impl;

import com.px.tool.model.CongNhanThanhPham;
import com.px.tool.repository.CongNhanThanhPhamRepository;
import com.px.tool.service.CongNhanThanhPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CongNhanThanhPhamServiceImpl implements CongNhanThanhPhamService {
    @Autowired
    private CongNhanThanhPhamRepository congNhanThanhPhamRepository;

    @Override
    public CongNhanThanhPham taoCongNhanThanhPham(CongNhanThanhPham congNhanThanhPham) {
        return null;
    }

    @Override
    public CongNhanThanhPham updateCongNhanThanhPham(CongNhanThanhPham congNhanThanhPham) {
        return null;
    }

    @Override
    public CongNhanThanhPham timCongNhanThanhPham(Long id) {
        return null;
    }

    @Override
    public List<CongNhanThanhPham> timCongNhanThanhPhamTheoPhongBan(Long userId) {
        return null;
    }
}
