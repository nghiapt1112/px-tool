package com.px.tool.domain.cntp.service.impl;

import com.px.tool.domain.cntp.CongNhanThanhPham;
import com.px.tool.domain.cntp.CongNhanThanhPhamPayload;
import com.px.tool.domain.cntp.repository.CongNhanThanhPhamRepository;
import com.px.tool.domain.cntp.repository.NoiDungThucHienRepository;
import com.px.tool.domain.cntp.service.CongNhanThanhPhamService;
import com.px.tool.domain.request.service.RequestService;
import com.px.tool.domain.user.User;
import com.px.tool.domain.user.service.UserService;
import com.px.tool.infrastructure.exception.PXException;
import com.px.tool.infrastructure.utils.DateTimeUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.px.tool.infrastructure.utils.CommonUtils.collectionAdd;

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
    public CongNhanThanhPham saveCongNhanThanhPham(Long userId, CongNhanThanhPhamPayload congNhanThanhPhamPayload) {
        if (Objects.isNull(congNhanThanhPhamPayload.getTpId())) {
            throw new RuntimeException("Thanh Pham phai co id");
        }
        CongNhanThanhPham existedCongNhanThanhPham = congNhanThanhPhamRepository
                .findById(congNhanThanhPhamPayload.getTpId())
                .orElse(null);

        User user = userService.findById(userId);
        congNhanThanhPhamPayload.capNhatChuKy(user);
        CongNhanThanhPham congNhanThanhPham = new CongNhanThanhPham();
        if (congNhanThanhPham.getTpId() == null) {
            congNhanThanhPham.setNgayGui(DateTimeUtils.nowAsMilliSec());
        }
        congNhanThanhPhamPayload.toEntity(congNhanThanhPham);
        congNhanThanhPhamPayload.capNhatNgayThangChuKy(congNhanThanhPham, existedCongNhanThanhPham);
        congNhanThanhPhamPayload.validateXacNhan(user, congNhanThanhPham, existedCongNhanThanhPham);
        // NOTE: tu dong chuyen den TP.KCS
//        if (congNhanThanhPhamPayload.allNhanVienKCSAssinged()) {
//            congNhanThanhPham.setTpkcsId(userService.findTPKCS().getUserId());
//        }

        cleanOldDetailData(congNhanThanhPhamPayload, existedCongNhanThanhPham);

        return congNhanThanhPhamRepository.save(congNhanThanhPham);
    }

    private void cleanOldDetailData(CongNhanThanhPhamPayload requestCNTP, CongNhanThanhPham existedCongNhanThanhPham) {
        if (Objects.isNull(requestCNTP)) {
            return;
        }
        try {
            Collection<Long> deletedIds = requestCNTP.getDeletedIds(existedCongNhanThanhPham);
            if (!CollectionUtils.isEmpty(deletedIds)) {
                noiDungThucHienRepository.deleteAllByIds(deletedIds);
            }
        } catch (Exception e) {
            throw new RuntimeException("Co loi xay ra khi clean noi_dung_thuc_hien");
        }
    }

    @Override
    public CongNhanThanhPhamPayload timCongNhanThanhPham(Long userId, Long id) {
//        Request request = requestService.findById(id);
        CongNhanThanhPham existedCNTP = congNhanThanhPhamRepository
                .findById(id)
                .orElseThrow(() -> new PXException("cntp.not_found"));
        CongNhanThanhPhamPayload payload = CongNhanThanhPhamPayload.fromEntity(existedCNTP);
        User currentUser = userService.findById(userId);

        if (currentUser.isQuanDocPhanXuong()) {
            List<Long> cusIds = new ArrayList<>(5);
            collectionAdd(cusIds, payload.getToTruong1Id(), payload.getToTruong2Id(), payload.getToTruong3Id(), payload.getToTruong4Id(), payload.getToTruong5Id());
            payload.setCusToTruongIds(cusIds);
        } else if (currentUser.isToTruong() && payload.noiDungThucHienFilled()) {
            payload.setCusToTruongIds(payload.getTpkcsId() != null ? Arrays.asList(payload.getTpkcsId()) : Collections.emptyList());
        }

        payload.setRequestId(existedCNTP.getTpId());
        payload.filterPermission(currentUser);

        payload.processSignImgAndFullName(userService.userById());
        return payload;
    }


}
