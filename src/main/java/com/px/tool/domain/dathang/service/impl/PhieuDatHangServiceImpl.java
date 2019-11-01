package com.px.tool.domain.dathang.service.impl;

import com.px.tool.domain.RequestType;
import com.px.tool.domain.dathang.PhieuDatHang;
import com.px.tool.domain.dathang.PhieuDatHangPayload;
import com.px.tool.domain.dathang.repository.PhieuDatHangDetailRepository;
import com.px.tool.domain.dathang.repository.PhieuDatHangRepository;
import com.px.tool.domain.dathang.service.PhieuDatHangService;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.service.RequestService;
import com.px.tool.infrastructure.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PhieuDatHangServiceImpl extends BaseServiceImpl implements PhieuDatHangService {
    @Autowired
    private PhieuDatHangRepository phieuDatHangRepository;

    @Autowired
    private PhieuDatHangDetailRepository phieuDatHangDetailRepository;

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
    @Transactional
    public PhieuDatHang save(PhieuDatHangPayload phieuDatHangPayload) {
        if (phieuDatHangPayload.notIncludeId()) {
            throw new RuntimeException("Phieu dat hang phai co id");
        }
        PhieuDatHang existedPhieuDatHang = phieuDatHangRepository
                .findById(phieuDatHangPayload.getPdhId())
                .orElse(null);
        cleanOldDetailData(existedPhieuDatHang);

        PhieuDatHang phieuDatHang = new PhieuDatHang();
        phieuDatHangPayload.toEntity(phieuDatHang);
        if (phieuDatHang.allApproved()) {
            existedPhieuDatHang.getRequest().setStatus(RequestType.PHUONG_AN);
            phieuDatHang.setRequest(existedPhieuDatHang.getRequest());
        }
        PhieuDatHang savedPhieuDatHang = phieuDatHangRepository.save(phieuDatHang);
        phieuDatHangDetailRepository.saveAll(savedPhieuDatHang.getPhieuDatHangDetails());
        return phieuDatHang;
    }

    private void cleanOldDetailData(PhieuDatHang existedPhieuDatHang) {
        try {
            logger.info("Dang clean da ta cu cua phieu dat hang");
            Set<Long> oldIds = null;
            if (existedPhieuDatHang != null) {
                oldIds = existedPhieuDatHang.getPhieuDatHangDetails()
                        .stream()
                        .map(detail -> detail.getPdhDetailId())
                        .collect(Collectors.toSet());
            }
            if (!CollectionUtils.isEmpty(oldIds)) {
                phieuDatHangDetailRepository.deleteAllByIds(oldIds);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Loi khi clean phieuDatHang Details");
        }
    }

    @Override
    public PhieuDatHang save(PhieuDatHang phieuDatHang) {
        return this.phieuDatHangRepository.save(phieuDatHang);
    }
}
