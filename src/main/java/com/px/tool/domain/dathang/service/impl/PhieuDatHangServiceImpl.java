package com.px.tool.domain.dathang.service.impl;

import com.px.tool.domain.RequestType;
import com.px.tool.domain.dathang.PhieuDatHang;
import com.px.tool.domain.dathang.PhieuDatHangPayload;
import com.px.tool.domain.dathang.repository.PhieuDatHangDetailRepository;
import com.px.tool.domain.dathang.repository.PhieuDatHangRepository;
import com.px.tool.domain.dathang.service.PhieuDatHangService;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.service.RequestService;
import com.px.tool.domain.user.User;
import com.px.tool.domain.user.service.UserService;
import com.px.tool.domain.vanbanden.VanBanDen;
import com.px.tool.domain.vanbanden.repository.VanBanDenRepository;
import com.px.tool.domain.vanbanden.service.VanBanDenServiceImpl;
import com.px.tool.infrastructure.exception.PXException;
import com.px.tool.infrastructure.logger.PXLogger;
import com.px.tool.infrastructure.service.impl.BaseServiceImpl;
import com.px.tool.infrastructure.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
public class PhieuDatHangServiceImpl extends BaseServiceImpl implements PhieuDatHangService {
    @Autowired
    private PhieuDatHangRepository phieuDatHangRepository;

    @Autowired
    private PhieuDatHangDetailRepository phieuDatHangDetailRepository;

    @Autowired
    private RequestService requestService;

    @Autowired
    private UserService userService;

    @Autowired
    private VanBanDenRepository vanBanDenRepository;

    @Override
    public List<PhieuDatHang> findByPhongBan(Long userId) {
        return phieuDatHangRepository.findByCreatedBy(userId);
    }

    @Override
    public PhieuDatHangPayload findById(Long userId, Long id) {
        Request request = requestService.findById(id);

        PhieuDatHangPayload payload = PhieuDatHangPayload
                .fromEntity(request.getPhieuDatHang())
                .filterPermission(userService.findById(userId));
        payload.setRequestId(request.getRequestId());
        payload.processSignImgAndFullName(userService.userById());
        return payload;
    }

    @Override
    @Transactional
    public PhieuDatHang save(Long userId, PhieuDatHangPayload phieuDatHangPayload) {
        if (phieuDatHangPayload.notIncludeId()) {
            throw new RuntimeException("Phieu dat hang phai co id");
        }
        PhieuDatHang existedPhieuDatHang = phieuDatHangRepository
                .findById(phieuDatHangPayload.getPdhId())
                .orElse(null);


        Long kiemHongReceiverId = existedPhieuDatHang.getRequest().getKiemHongReceiverId();
        Long phieuDatHangReceiverId = Objects.isNull(phieuDatHangPayload.getNoiNhan()) ? userId : phieuDatHangPayload.getNoiNhan();
        Long phuongAnReceiverId = existedPhieuDatHang.getRequest().getPhuongAnReceiverId();
        Long cntpReceiverId = existedPhieuDatHang.getRequest().getCntpReceiverId();

        User user = userService.findById(userId);

        phieuDatHangPayload.capNhatChuKy(user);
        Long requestId = existedPhieuDatHang.getRequest().getRequestId();
        PhieuDatHang phieuDatHang = new PhieuDatHang();
        phieuDatHangPayload.toEntity(phieuDatHang);
        phieuDatHangPayload.capNhatNgayThangChuKy(phieuDatHang, existedPhieuDatHang);
        if (phieuDatHang.allApproved()) {
            existedPhieuDatHang.getRequest().setStatus(RequestType.DAT_HANG);
            guiVanBanDen(phieuDatHangPayload);
            // clear back recieverid
            phuongAnReceiverId = null;
            phieuDatHangReceiverId = phieuDatHangPayload.getNoiNhan();
            kiemHongReceiverId = null;
            cntpReceiverId = null;
        }
        cleanOldDetailData(phieuDatHangPayload, existedPhieuDatHang);
        requestService.updateReceiveId(requestId, kiemHongReceiverId, phieuDatHangReceiverId, phuongAnReceiverId, cntpReceiverId);
        phieuDatHangRepository.save(phieuDatHang);

        return phieuDatHang;
    }

    @Transactional
    public void guiVanBanDen(PhieuDatHangPayload payload) {
        try {
            VanBanDen vanBanDen = new VanBanDen();
            vanBanDen.setNoiDung(payload.getCusNoiDung());
            vanBanDen.setNoiNhan(CommonUtils.toString(payload.getCusReceivers()));
            vanBanDen.setRequestType(RequestType.PHUONG_AN);
            vanBanDen.setRead(false);
            vanBanDen.setSoPa("số: " + payload.getPdhId());
            vanBanDenRepository.save(vanBanDen);
        } catch (Exception e) {
            throw new PXException("[Đặt Hàng]: Có lỗi khi gửi văn bản đến.");
        }
    }


    private void cleanOldDetailData(PhieuDatHangPayload requestPhieuDatHang, PhieuDatHang existedPhieuDatHang) {
        try {
            PXLogger.info("Dang clean da ta cu cua phieu dat hang");
            Collection<Long> deleteIds = requestPhieuDatHang.getDeletedIds(existedPhieuDatHang);
            if (!CollectionUtils.isEmpty(deleteIds)) {
                phieuDatHangDetailRepository.deleteAllByIds(deleteIds);
            }
        } catch (Exception e) {
            throw new PXException("dathang.clean_error");
        }
    }

    @Override
    public PhieuDatHang save(PhieuDatHang phieuDatHang) {
        return this.phieuDatHangRepository.save(phieuDatHang);
    }

    @Override
    public List<PhieuDatHang> findListCongViecCuaTLKT(Long userId) {
        return phieuDatHangRepository.findListCongViecCuaTLKT(userId);
    }
}
