package com.px.tool.domain.kiemhong.service.impl;

import com.px.tool.domain.RequestType;
import com.px.tool.domain.cntp.CongNhanThanhPham;
import com.px.tool.domain.dathang.PhieuDatHang;
import com.px.tool.domain.dathang.PhieuDatHangDetail;
import com.px.tool.domain.dathang.repository.PhieuDatHangDetailRepository;
import com.px.tool.domain.dathang.repository.PhieuDatHangRepository;
import com.px.tool.domain.kiemhong.KiemHong;
import com.px.tool.domain.kiemhong.KiemHongDetail;
import com.px.tool.domain.kiemhong.KiemHongPayLoad;
import com.px.tool.domain.kiemhong.repository.KiemHongDetailRepository;
import com.px.tool.domain.kiemhong.repository.KiemHongRepository;
import com.px.tool.domain.kiemhong.service.KiemHongService;
import com.px.tool.domain.phuongan.PhuongAn;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.service.RequestService;
import com.px.tool.domain.user.User;
import com.px.tool.domain.user.service.UserService;
import com.px.tool.infrastructure.exception.PXException;
import com.px.tool.infrastructure.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class KiemHongServiceImpl implements KiemHongService {
    @Autowired
    private KiemHongRepository kiemHongRepository;

    @Autowired
    private KiemHongDetailRepository kiemHongDetailRepository;

    @Autowired
    private RequestService requestService;

    @Autowired
    private PhieuDatHangRepository phieuDatHangRepository;

    @Autowired
    private PhieuDatHangDetailRepository phieuDatHangDetailRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<KiemHongPayLoad> findThongTinKiemHongCuaPhongBan(Long userId) {
        // TODO: tu thong tin user, get tat ca kiemHong dang can xu ly 1 phong ban.
        //  => viec luu createdId nen de la phongBan id.
        return kiemHongRepository.findByCreatedBy(userId)
                .stream()
                .filter(Objects::nonNull)
                .map(KiemHongPayLoad::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public KiemHongPayLoad findThongTinKiemHong(Long id) {
        Request request = requestService.findById(id);
        if (request != null) {
            return KiemHongPayLoad
                    .fromEntity(request.getKiemHong())
                    .andRequestId(request.getRequestId());
        }
        throw new RuntimeException("Kiem hong not found");
    }

    @Override
    @Transactional
    public KiemHongPayLoad save(Long currentUserId, KiemHongPayLoad kiemHongPayLoad) {

        try {
            if (kiemHongPayLoad.notIncludeId()) {
                validateTaoKiemHong(currentUserId, kiemHongPayLoad);

                KiemHong kiemHong = new KiemHong();
                kiemHongPayLoad.toEntity(kiemHong);
                kiemHong.setCreatedBy(currentUserId);

                Request request = new Request();
                request.setCreatedBy(currentUserId);
                request.setKiemHong(kiemHong);
                request.setCongNhanThanhPham(new CongNhanThanhPham());
                request.setPhuongAn(new PhuongAn());
                request.setPhieuDatHang(new PhieuDatHang());
                request.setStatus(RequestType.KIEM_HONG);
                request.setKiemHongReceiverId(Objects.isNull(kiemHongPayLoad.getNoiNhan()) ? currentUserId : kiemHongPayLoad.getNoiNhan());
                Request savedRequest = this.requestService.save(request);

                KiemHong savedKiemHong = savedRequest.getKiemHong();
                kiemHong.getKiemHongDetails()
                        .forEach(el -> el.setKiemHong(savedKiemHong));
                kiemHongDetailRepository.saveAll(kiemHong.getKiemHongDetails());
                return KiemHongPayLoad
                        .fromEntity(savedRequest.getKiemHong())
                        .andRequestId(savedRequest.getRequestId());
            } else {
                return capNhatKiemHong(currentUserId, kiemHongPayLoad);
            }
        } catch (Exception e) {
            if (e instanceof PXException) {
                throw e;
            } else {
                throw new PXException("Co loi trong qua trinh save Kiem Hong");
            }
        }

    }

    /**
     * Chỉ tổ trưởng mới có quyền lập  phiếu
     *
     * Khi lập phiếu thì không được có xác nhận.
     */
    private void validateTaoKiemHong(Long currentUserId, KiemHongPayLoad kiemHongPayLoad) {
        User user = userService.findById(currentUserId);
        if (!user.isToTruong()) {
            throw new PXException("Chỉ tổ trưởng mới có quyền lập phiếu");
        }
        if (kiemHongPayLoad.getToTruongXacNhan() || kiemHongPayLoad.getQuanDocXacNhan() || kiemHongPayLoad.getTroLyKTXacNhan()) {
            throw new PXException("Xác nhận dành cho tổ trưởng.");
        }
    }

    @Override
    public KiemHongPayLoad capNhatKiemHong(Long userId, KiemHongPayLoad kiemHongPayLoad) {
        KiemHong existedKiemHong = kiemHongRepository
                .findById(kiemHongPayLoad.getKhId())
                .orElseThrow(() -> new RuntimeException("Khong tim thay kiem hong"));

        cleanKiemHongDetails(existedKiemHong);
        KiemHong requestKiemHong = new KiemHong();
        kiemHongPayLoad.toEntity(requestKiemHong);

        // TODO: get current user info , check permission. vi khong phai ai cung approve dc cho nguoi khac
        Long requestId = existedKiemHong.getRequest().getRequestId();
        PhieuDatHang pdh = existedKiemHong.getRequest().getPhieuDatHang();
        Long kiemHongReceiverId = Objects.isNull(kiemHongPayLoad.getNoiNhan()) ? userId : kiemHongPayLoad.getNoiNhan();
        Long phieuDatHangReceiverId = existedKiemHong.getRequest().getPhieuDatHangReceiverId();
        Long phuongAnReceiverId = existedKiemHong.getRequest().getPhuongAnReceiverId();
        Long cntpReceiverId = existedKiemHong.getRequest().getCntpReceiverId();
        if (requestKiemHong.allApproved()) {
            existedKiemHong.getRequest().setStatus(RequestType.DAT_HANG);
            requestKiemHong.setRequest(existedKiemHong.getRequest());
            phieuDatHangReceiverId = kiemHongPayLoad.getNoiNhan();
            createPhieuDatHang(requestKiemHong, pdh);
        }
        capNhatNgayThangChuKy(requestKiemHong, existedKiemHong);
        validateXacNhan(userId, requestKiemHong, existedKiemHong);
        requestService.updateReceiveId(requestId, kiemHongReceiverId, phieuDatHangReceiverId, phuongAnReceiverId, cntpReceiverId);
        kiemHongRepository.save(requestKiemHong);
        kiemHongPayLoad.setRequestId(requestId);
        return kiemHongPayLoad;
    }

    /**
     * Phai dung permission khi xac nhan
     * Khi Chuyen thi phai co xac nhan, xac nhan thi phai co chuyen
     */
    private void validateXacNhan(Long userId, KiemHong requestKiemHong, KiemHong existedKiemHong) {
        if ((requestKiemHong.getTroLyKTXacNhan() || requestKiemHong.getQuanDocXacNhan() || requestKiemHong.getToTruongXacNhan()) && requestKiemHong.getNoiNhan() == null) {
            throw new PXException("Nơi nhận phải được chọn");
        }
        if (!requestKiemHong.getTroLyKTXacNhan() && !requestKiemHong.getQuanDocXacNhan() && !requestKiemHong.getToTruongXacNhan() && requestKiemHong.getNoiNhan() != null) {
            throw new PXException("Phải có người xác nhận");
        }
        User user = userService.findById(userId);
        if(user.isToTruong() && !requestKiemHong.getToTruongXacNhan() && (requestKiemHong.getQuanDocXacNhan() || requestKiemHong.getTroLyKTXacNhan())) {
            throw new PXException("Tổ trưởng xác nhận ở ô tổ trưởng");
        }
        if(user.isTroLyKT() && !requestKiemHong.getTroLyKTXacNhan() && (requestKiemHong.getQuanDocXacNhan() || requestKiemHong.getToTruongXacNhan())) {
            throw new PXException("Trợ lý KT xác nhận ở ô trợ lý KT");
        }
        if(user.isQuanDocPhanXuong() && !requestKiemHong.getQuanDocXacNhan() && (requestKiemHong.getToTruongXacNhan() || requestKiemHong.getTroLyKTXacNhan())) {
            throw new PXException("Quản đốc xác nhận ở ô quản đốc");
        }
    }

    /**
     * khi co xac nhan thi cap nhat ngay thang
     */
    private void capNhatNgayThangChuKy(KiemHong requestKiemHong, KiemHong existedKiemHong) {
        if (requestKiemHong.getToTruongXacNhan() != existedKiemHong.getToTruongXacNhan()) {
            requestKiemHong.setNgayThangNamToTruong(DateTimeUtils.nowAsString());
        }
        if (requestKiemHong.getQuanDocXacNhan() != existedKiemHong.getQuanDocXacNhan()) {
            requestKiemHong.setNgayThangNamQuanDoc(DateTimeUtils.nowAsString());
        }
        if (requestKiemHong.getTroLyKTXacNhan() != existedKiemHong.getTroLyKTXacNhan()) {
            requestKiemHong.setNgayThangNamTroLyKT(DateTimeUtils.nowAsString());
        }
    }

    private void createPhieuDatHang(KiemHong requestKiemHong, PhieuDatHang pdh) {
        pdh.setSo(requestKiemHong.getSoHieu());
        pdh.setDonViYeuCau(requestKiemHong.getToSX()); // C3 sheet1
        pdh.setPhanXuong(requestKiemHong.getPhanXuong()); // C2 sheet1
        pdh.setNoiDung(requestKiemHong.getNguonVao() + " " + requestKiemHong.getTenVKTBKT() + " " + requestKiemHong.getSoHieu() + " " + requestKiemHong.getSoXX()); // E2 sheet1 + E1 sheet1
        PhieuDatHang savedPdh = phieuDatHangRepository.save(pdh);

        Set<PhieuDatHangDetail> phieuDatHangDetails = new HashSet<>(requestKiemHong.getKiemHongDetails().size());
        PhieuDatHangDetail detail = null;
        for (KiemHongDetail kiemHongDetail : requestKiemHong.getKiemHongDetails()) {
            detail = new PhieuDatHangDetail();
            detail.setKiemHongDetailId(kiemHongDetail.getKhDetailId());
            detail.setTenPhuKien(kiemHongDetail.getTenPhuKien());
            detail.setTenVatTuKyThuat(kiemHongDetail.getTenLinhKien());
            detail.setKiMaHieu(kiemHongDetail.getKyHieu());
            detail.setDvt("Cái");
            detail.setSl(kiemHongDetail.getSl());
            detail.setPhuongPhapKhacPhuc(kiemHongDetail.getPhuongPhapKhacPhuc());
            detail.setPhieuDatHang(savedPdh);
            phieuDatHangDetails.add(detail);
        }
        phieuDatHangDetailRepository.saveAll(phieuDatHangDetails);
    }

    @Override
    public boolean isExisted(Long id) {
        return kiemHongRepository.existsById(id);
    }

    public void cleanKiemHongDetails(KiemHong existedKiemHong) {
        try {
            if (Objects.isNull(existedKiemHong)) {
                return;
            }
            Set<Long> kiemHongDetailIds = existedKiemHong.getKiemHongDetails()
                    .stream()
                    .map(KiemHongDetail::getKhDetailId)
                    .collect(Collectors.toSet());
            if (CollectionUtils.isEmpty(kiemHongDetailIds)) {
                return;
            }
            kiemHongDetailRepository.deleteAllByIds(kiemHongDetailIds);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Clean kiemhong detail khong thanh cong.");
        }
    }
}
