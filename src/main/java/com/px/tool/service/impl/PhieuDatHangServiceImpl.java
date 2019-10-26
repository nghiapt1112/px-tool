package com.px.tool.service.impl;

import com.px.tool.model.PhieuDatHang;
import com.px.tool.repository.PhieuDatHangRepository;
import com.px.tool.service.PhieuDatHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhieuDatHangServiceImpl implements PhieuDatHangService {
    @Autowired
    private PhieuDatHangRepository phieuDatHangRepository;

    @Override
    public List<PhieuDatHang> findByPhongBan(Long userId) {
        return phieuDatHangRepository.findByCreatedBy(userId);
    }

    @Override
    public PhieuDatHang findById(Long id) {
        return phieuDatHangRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Phieu dat hang not found: requestId = " + id));
    }

    @Override
    public PhieuDatHang create(PhieuDatHang phieuDatHang) {
        return this.phieuDatHangRepository.save(phieuDatHang);
    }

    @Override
    public PhieuDatHang save(PhieuDatHang phieuDatHang) {
        return this.phieuDatHangRepository.save(phieuDatHang);
    }
    
}
