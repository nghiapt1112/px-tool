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
import com.px.tool.domain.vanbanden.service.VanBanDenServiceImpl;
import com.px.tool.infrastructure.exception.PXException;
import com.px.tool.infrastructure.service.impl.BaseServiceImpl;
import com.px.tool.infrastructure.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.px.tool.domain.user.repository.UserRepository.group_12_PLUS;

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
    private VanBanDenServiceImpl vanBanDenService;

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
        capNhatNgayThangChuKy(phieuDatHang, existedPhieuDatHang);
        validateXacNhan(user, phieuDatHang, existedPhieuDatHang);
        if (phieuDatHang.allApproved()) {
            existedPhieuDatHang.getRequest().setStatus(RequestType.DAT_HANG);
//            phieuDatHang.setRequest(existedPhieuDatHang.getRequest());
            guiVanBanDen();
            // clear back recieverid
            phuongAnReceiverId = null;
            phieuDatHangReceiverId = phieuDatHangPayload.getNoiNhan();
            kiemHongReceiverId = null;
            cntpReceiverId = null;
        }
        cleanOldDetailData(phieuDatHang, existedPhieuDatHang);
        requestService.updateReceiveId(requestId, kiemHongReceiverId, phieuDatHangReceiverId, phuongAnReceiverId, cntpReceiverId);
        phieuDatHangRepository.save(phieuDatHang);

        return phieuDatHang;
    }

    private void guiVanBanDen() {
        vanBanDenService.guiVanBanDen(group_12_PLUS, RequestType.DAT_HANG);
    }

    /**
     * Phai dung permission khi xac nhan
     * Khi Chuyen thi phai co xac nhan, xac nhan thi phai co chuyen
     */
    private void validateXacNhan(User user, PhieuDatHang requestPhieuDH, PhieuDatHang existedPDH) {

        if ((requestPhieuDH.getNguoiDatHangXacNhan() || requestPhieuDH.getTpvatTuXacNhan()) && !user.isTruongPhongKTHK() && requestPhieuDH.getNoiNhan() == null) {
            throw new PXException("noi_nhan.must_choose");
        }
        if (!requestPhieuDH.getNguoiDatHangXacNhan() && !requestPhieuDH.getTpvatTuXacNhan() && !requestPhieuDH.getTpkthkXacNhan()) {
            throw new PXException("Phải có người xác nhận");
        }
        if (user.isNhanVienVatTu()) {
            requestPhieuDH.setTpkthkXacNhan(existedPDH.getTpkthkXacNhan());
            requestPhieuDH.setTpvatTuXacNhan(existedPDH.getTpvatTuXacNhan());
        }
        if (user.isTruongPhongVatTu()) {
            requestPhieuDH.setTpkthkXacNhan(existedPDH.getTpkthkXacNhan());
            requestPhieuDH.setNguoiDatHangXacNhan(existedPDH.getNguoiDatHangXacNhan());
        }
        if (user.isTruongPhongKTHK()) {
            requestPhieuDH.setNguoiDatHangXacNhan(existedPDH.getTpkthkXacNhan());
            requestPhieuDH.setNguoiDatHangXacNhan(existedPDH.getNguoiDatHangXacNhan());
        }
    }

    /**
     * khi co xac nhan thi cap nhat ngay thang
     */
    private void capNhatNgayThangChuKy(PhieuDatHang phieuDatHang, PhieuDatHang existedPhieuDatHang) {
        if (phieuDatHang.getNguoiDatHangXacNhan() != existedPhieuDatHang.getNguoiDatHangXacNhan()) {
            phieuDatHang.setNguoiDatHang(DateTimeUtils.nowAsString());
        }
        if (phieuDatHang.getTpvatTuXacNhan() != existedPhieuDatHang.getTpvatTuXacNhan()) {
            phieuDatHang.setNgayThangNamTPVatTu(DateTimeUtils.nowAsString());
        }
        if (phieuDatHang.getTpkthkXacNhan() != existedPhieuDatHang.getTpkthkXacNhan()) {
            phieuDatHang.setNgayThangNamTPKTHK(DateTimeUtils.nowAsString());
        }
    }

    private void cleanOldDetailData(PhieuDatHang requestPhieuDatHang, PhieuDatHang existedPhieuDatHang) {
        try {
            logger.info("Dang clean da ta cu cua phieu dat hang");
            Set<Long> deleteIds = null;
            if (existedPhieuDatHang != null) {
                Set<Long> requestIds = requestPhieuDatHang.getPhieuDatHangDetails()
                        .stream()
                        .map(el -> el.getPdhDetailId())
                        .collect(Collectors.toSet());
                deleteIds = existedPhieuDatHang.getPhieuDatHangDetails()
                        .stream()
                        .map(detail -> detail.getPdhDetailId())
                        .filter(el -> !requestIds.contains(el))
                        .collect(Collectors.toSet());
            }
            if (!CollectionUtils.isEmpty(deleteIds)) {
                phieuDatHangDetailRepository.deleteAllByIds(deleteIds);
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

    @Override
    public List<PhieuDatHang> findListCongViecCuaTLKT(Long userId) {
        return phieuDatHangRepository.findListCongViecCuaTLKT(userId);
    }
}
