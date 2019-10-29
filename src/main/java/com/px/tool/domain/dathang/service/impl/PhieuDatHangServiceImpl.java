package com.px.tool.domain.dathang.service.impl;

import com.px.tool.domain.dathang.PhieuDatHang;
import com.px.tool.domain.dathang.PhieuDatHangPayload;
import com.px.tool.domain.dathang.repository.PhieuDatHangRepository;
import com.px.tool.domain.dathang.service.PhieuDatHangService;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhieuDatHangServiceImpl implements PhieuDatHangService {
    @Autowired
    private PhieuDatHangRepository phieuDatHangRepository;

    @Autowired
    private RequestService requestService;

    @Override
    public List<PhieuDatHang> findByPhongBan(Long userId) {
        return phieuDatHangRepository.findByCreatedBy(userId);
    }

    @Override
    public PhieuDatHangPayload findById(Long id) {
        Request request = requestService.findById(id);
        PhieuDatHangPayload payload = PhieuDatHangPayload.fromEntity(request.getPhieuDatHang());
        payload.setRequestId(request.getRequestId());
        return payload;
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
