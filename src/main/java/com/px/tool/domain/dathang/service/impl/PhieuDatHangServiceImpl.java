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
import com.px.tool.infrastructure.exception.PXException;
import com.px.tool.infrastructure.service.impl.BaseServiceImpl;
import com.px.tool.infrastructure.utils.DateTimeUtils;
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

    @Autowired
    private UserService userService;

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
    public PhieuDatHang save(Long userId, PhieuDatHangPayload phieuDatHangPayload) {
        if (phieuDatHangPayload.notIncludeId()) {
            throw new RuntimeException("Phieu dat hang phai co id");
        }
        PhieuDatHang existedPhieuDatHang = phieuDatHangRepository
                .findById(phieuDatHangPayload.getPdhId())
                .orElse(null);


        Long kiemHongReceiverId = existedPhieuDatHang.getRequest().getKiemHongReceiverId();
        Long phieuDatHangReceiverId = existedPhieuDatHang.getRequest().getPhieuDatHangReceiverId();
        Long phuongAnReceiverId = existedPhieuDatHang.getRequest().getPhuongAnReceiverId();
        Long cntpReceiverId = existedPhieuDatHang.getRequest().getCntpReceiverId();

        Long requestId = existedPhieuDatHang.getRequest().getRequestId();
        PhieuDatHang phieuDatHang = new PhieuDatHang();
        phieuDatHangPayload.toEntity(phieuDatHang);
        capNhatNgayThangChuKy(phieuDatHang, existedPhieuDatHang);
        validateXacNhan(userId, phieuDatHang, existedPhieuDatHang);
        if (phieuDatHang.allApproved()) {
            existedPhieuDatHang.getRequest().setStatus(RequestType.PHUONG_AN);
            phieuDatHang.setRequest(existedPhieuDatHang.getRequest());
            phuongAnReceiverId = phieuDatHangPayload.getNoiNhan();
        }
        cleanOldDetailData(existedPhieuDatHang);
        requestService.updateReceiveId(requestId, kiemHongReceiverId, phieuDatHangReceiverId, phuongAnReceiverId, cntpReceiverId);
        phieuDatHangRepository.save(phieuDatHang);
        return phieuDatHang;
    }

    /**
     * Phai dung permission khi xac nhan
     * Khi Chuyen thi phai co xac nhan, xac nhan thi phai co chuyen
     */
    private void validateXacNhan(Long userId, PhieuDatHang requestPhieuDH, PhieuDatHang existedPDH) {
        if ((requestPhieuDH.getNguoiDatHangXacNhan() || requestPhieuDH.getTpvatTuXacNhan() || requestPhieuDH.getTpkthkXacNhan()) && requestPhieuDH.getNoiNhan() == null) {
            throw new PXException("Nơi nhận phải được chọn");
        }
        if (!requestPhieuDH.getNguoiDatHangXacNhan() && !requestPhieuDH.getTpvatTuXacNhan() && !requestPhieuDH.getTpkthkXacNhan()) {
            throw new PXException("Phải có người xác nhận");
        }
        User user = userService.findById(userId);
        if (user.isNhanVienVatTu()) {
//            throw new PXException("Người giao hàng xác nhận ở người giao hàng");
            requestPhieuDH.setTpkthkXacNhan(existedPDH.getTpkthkXacNhan());
            requestPhieuDH.setTpvatTuXacNhan(existedPDH.getTpvatTuXacNhan());
        }
        if (user.isTruongPhongVatTu()) {
//            throw new PXException("Trưởng phòng vật tư xác nhận ở trưởng phòng vật tư");
            requestPhieuDH.setTpkthkXacNhan(existedPDH.getTpkthkXacNhan());
            requestPhieuDH.setNguoiDatHangXacNhan(existedPDH.getNguoiDatHangXacNhan());
        }
        if (user.isTruongPhongKTHK()) {
//            throw new PXException("Trưởng phòng KTHK xác nhận ở trưởng phòng KTHK");
            requestPhieuDH.setTpkthkXacNhan(existedPDH.getTpkthkXacNhan());
            requestPhieuDH.setNguoiDatHangXacNhan(existedPDH.getNguoiDatHangXacNhan());
        }
    }

    /**
     * khi co xac nhan thi cap nhat ngay thang
     */
    private void capNhatNgayThangChuKy(PhieuDatHang phieuDatHang, PhieuDatHang existedPhieuDatHang) {
        if (phieuDatHang.getNguoiDatHang() != existedPhieuDatHang.getNguoiDatHang()) {
            phieuDatHang.setNguoiDatHang(DateTimeUtils.nowAsString());
        }
        if (phieuDatHang.getTpvatTuXacNhan() != existedPhieuDatHang.getTpvatTuXacNhan()) {
            phieuDatHang.setNgayThangNamTPVatTu(DateTimeUtils.nowAsString());
        }
        if (phieuDatHang.getTpkthkXacNhan() != existedPhieuDatHang.getTpkthkXacNhan()) {
            phieuDatHang.setNgayThangNamTPKTHK(DateTimeUtils.nowAsString());
        }
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
