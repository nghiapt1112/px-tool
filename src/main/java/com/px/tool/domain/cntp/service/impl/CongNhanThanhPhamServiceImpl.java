package com.px.tool.domain.cntp.service.impl;

import com.px.tool.domain.cntp.CongNhanThanhPham;
import com.px.tool.domain.cntp.CongNhanThanhPhamPayload;
import com.px.tool.domain.cntp.NoiDungThucHien;
import com.px.tool.domain.cntp.repository.CongNhanThanhPhamRepository;
import com.px.tool.domain.cntp.repository.NoiDungThucHienRepository;
import com.px.tool.domain.cntp.service.CongNhanThanhPhamService;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.service.RequestService;
import com.px.tool.domain.user.service.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CongNhanThanhPhamServiceImpl implements CongNhanThanhPhamService {
    @Autowired
    private CongNhanThanhPhamRepository congNhanThanhPhamRepository;

    @Autowired
    private RequestService requestService;

    @Autowired
    private NoiDungThucHienRepository noiDungThucHienRepository;

    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public CongNhanThanhPham taoCongNhanThanhPham(CongNhanThanhPhamPayload congNhanThanhPhamPayload) {
        if (Objects.isNull(congNhanThanhPhamPayload.getTpId())) {
            throw new RuntimeException("Thanh Pham phai co id");
        }
        CongNhanThanhPham existedCongNhanThanhPham = congNhanThanhPhamRepository
                .findById(congNhanThanhPhamPayload.getTpId())
                .orElse(null);

        CongNhanThanhPham congNhanThanhPham = new CongNhanThanhPham();
        congNhanThanhPhamPayload.toEntity(congNhanThanhPham);
        cleanOldDetailData(congNhanThanhPham, existedCongNhanThanhPham);

        return congNhanThanhPhamRepository.save(congNhanThanhPham);
    }

    private void cleanOldDetailData(CongNhanThanhPham requestCNTP, CongNhanThanhPham existedCongNhanThanhPham) {
        if (Objects.isNull(existedCongNhanThanhPham)) {
            return;
        }
        try {

            Set<Long> requestIds = requestCNTP.getNoiDungThucHiens()
                    .stream()
                    .map(NoiDungThucHien::getNoiDungId)
                    .collect(Collectors.toSet());

            Set<Long> ids = existedCongNhanThanhPham.getNoiDungThucHiens()
                    .stream()
                    .map(NoiDungThucHien::getNoiDungId)
                    .filter(el -> !requestIds.contains(el))
                    .collect(Collectors.toSet());

            if (!CollectionUtils.isEmpty(ids)) {
                noiDungThucHienRepository.deleteAllByIds(ids);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Co loi xay ra khi clean noi_dung_thuc_hien");
        }
    }

    @Override
    public CongNhanThanhPham updateCongNhanThanhPham(CongNhanThanhPham congNhanThanhPham) {
        return congNhanThanhPhamRepository.save(congNhanThanhPham);
    }

    @Override
    public CongNhanThanhPhamPayload timCongNhanThanhPham(Long userId, Long id) {
        Request request = requestService.findById(id);
        CongNhanThanhPhamPayload payload = CongNhanThanhPhamPayload.fromEntity(request.getCongNhanThanhPham());
        payload.setRequestId(request.getRequestId());
        payload.filterPermission(userService.findById(userId));
        return payload;
    }

    @Override
    public List<CongNhanThanhPham> timCongNhanThanhPhamTheoPhongBan(Long userId) {
        return Collections.emptyList();
    }
}
