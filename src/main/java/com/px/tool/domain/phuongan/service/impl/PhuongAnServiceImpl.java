package com.px.tool.domain.phuongan.service.impl;

import com.px.tool.domain.phuongan.PhuongAn;
import com.px.tool.domain.phuongan.PhuongAnPayload;
import com.px.tool.domain.phuongan.repository.PhuongAnRepository;
import com.px.tool.domain.phuongan.service.PhuongAnService;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhuongAnServiceImpl implements PhuongAnService {
    @Autowired
    private PhuongAnRepository phuongAnRepository;

    @Autowired
    private RequestService requestService;

    @Override
    public PhuongAnPayload findById(Long id) {
        Request request = requestService.findById(id);
        PhuongAnPayload payload = PhuongAnPayload.fromEntity(request.getPhuongAn());
        payload.setRequestId(request.getRequestId());
        return payload;
    }

    @Override
    public List<PhuongAn> findByPhongBan(Long userId) {
        return null;
    }

    @Override
    public PhuongAn createPhuongAn(PhuongAn phuongAn) {
        return this.phuongAnRepository.save(phuongAn);
    }

    @Override
    public PhuongAn savePhuongAn(PhuongAn phuongAn) {
        return this.phuongAnRepository.save(phuongAn);
    }
}
