package com.px.tool.domain.cntp.service.impl;

import com.px.tool.domain.cntp.CongNhanThanhPham;
import com.px.tool.domain.cntp.CongNhanThanhPhamPayload;
import com.px.tool.domain.cntp.repository.CongNhanThanhPhamRepository;
import com.px.tool.domain.cntp.service.CongNhanThanhPhamService;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CongNhanThanhPhamServiceImpl implements CongNhanThanhPhamService {
    @Autowired
    private CongNhanThanhPhamRepository congNhanThanhPhamRepository;

    @Autowired
    private RequestService requestService;

    @Override
    public CongNhanThanhPham taoCongNhanThanhPham(CongNhanThanhPhamPayload congNhanThanhPhamPayload) {
        CongNhanThanhPham congNhanThanhPham = congNhanThanhPhamPayload.toEntity();
        Request request = this.requestService.findById(congNhanThanhPhamPayload.getRequestId());
        congNhanThanhPham.setRequest(request);
        return congNhanThanhPhamRepository.save(congNhanThanhPham);
    }

    @Override
    public CongNhanThanhPham updateCongNhanThanhPham(CongNhanThanhPham congNhanThanhPham) {
        return congNhanThanhPhamRepository.save(congNhanThanhPham);
    }

    @Override
    public CongNhanThanhPhamPayload timCongNhanThanhPham(Long id) {
        Request request = requestService.findById(id);
        CongNhanThanhPhamPayload payload = CongNhanThanhPhamPayload.fromEntity(request.getCongNhanThanhPham());
        payload.setRequestId(request.getRequestId());
        return payload;
    }

    @Override
    public List<CongNhanThanhPham> timCongNhanThanhPhamTheoPhongBan(Long userId) {
        return Collections.emptyList();
    }
}
