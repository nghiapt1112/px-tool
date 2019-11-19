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
import com.px.tool.domain.user.repository.UserRepository;
import com.px.tool.domain.user.service.UserService;
import com.px.tool.domain.vanbanden.VanBanDen;
import com.px.tool.domain.vanbanden.repository.VanBanDenRepository;
import com.px.tool.infrastructure.exception.PXException;
import com.px.tool.infrastructure.service.impl.BaseServiceImpl;
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

import static com.px.tool.domain.user.repository.UserRepository.group_12_PLUS;

@Service
public class KiemHongServiceImpl extends BaseServiceImpl implements KiemHongService {
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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VanBanDenRepository vanBanDenRepository;
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
    public KiemHongPayLoad findThongTinKiemHong(Long userId, Long id) {
        Request request = requestService.findById(id);

        if (request.getKiemHong() == null) {
            throw new RuntimeException("kiemhong.not_found");
        }
        return KiemHongPayLoad
                .fromEntity(request.getKiemHong())
                .andRequestId(request.getRequestId())
                .filterPermission(userService.findById(userId));
    }

    @Override
    @Transactional
    public KiemHongPayLoad save(Long currentUserId, KiemHongPayLoad kiemHongPayLoad) {

        try {
            if (!kiemHongPayLoad.includedId()) {
                validateTaoKiemHong(currentUserId, kiemHongPayLoad);

                KiemHong kiemHong = new KiemHong();
                kiemHongPayLoad.toEntity(kiemHong);
                kiemHong.setCreatedBy(currentUserId);

                kiemHong.setGiamDocXacNhan(false);
                kiemHong.setQuanDocXacNhan(false);
                kiemHong.setToTruongXacNhan(false);
                kiemHong.setTroLyKTXacNhan(false);

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
     * <p>
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
    @Transactional
    public KiemHongPayLoad capNhatKiemHong(Long userId, KiemHongPayLoad kiemHongPayLoad) {
        if (CollectionUtils.isEmpty(kiemHongPayLoad.getKiemHongDetails())) {
            throw new PXException("Thông tin chi tiết phải được điền.");
        }
        KiemHong existedKiemHong = kiemHongRepository
                .findById(kiemHongPayLoad.getKhId())
                .orElseThrow(() -> new PXException("Không tìm thấy kiểm hỏng"));


        KiemHong requestKiemHong = new KiemHong();
        kiemHongPayLoad.toEntity(requestKiemHong);
        capNhatNgayThangChuKy(requestKiemHong, existedKiemHong);
        validateXacNhan(userId, requestKiemHong, existedKiemHong);
        cleanKiemHongDetails(requestKiemHong, existedKiemHong);
        // TODO: get current user info , check permission. vi khong phai ai cung approve dc cho nguoi khac
        Long requestId = existedKiemHong.getRequest().getRequestId();
        PhieuDatHang pdh = existedKiemHong.getRequest().getPhieuDatHang();
        Long kiemHongReceiverId = Objects.isNull(kiemHongPayLoad.getNoiNhan()) ? userId : kiemHongPayLoad.getNoiNhan();
        Long phieuDatHangReceiverId = existedKiemHong.getRequest().getPhieuDatHangReceiverId();
        Long phuongAnReceiverId = existedKiemHong.getRequest().getPhuongAnReceiverId();
        Long cntpReceiverId = existedKiemHong.getRequest().getCntpReceiverId();
        if (requestKiemHong.allApproved()) {
            if (Objects.isNull(kiemHongPayLoad.getNoiNhan())) {
                throw new PXException("noi_nhan.must_choose");
            }
            existedKiemHong.getRequest().setStatus(RequestType.DAT_HANG);
            requestKiemHong.setRequest(existedKiemHong.getRequest());
            phieuDatHangReceiverId = kiemHongPayLoad.getNoiNhan();
            createPhieuDatHang(requestKiemHong, pdh);
            guiVanBanDen();
        }

        requestService.updateReceiveId(requestId, kiemHongReceiverId, phieuDatHangReceiverId, phuongAnReceiverId, cntpReceiverId);
        kiemHongRepository.save(requestKiemHong);
        kiemHongPayLoad.setRequestId(requestId);
        return kiemHongPayLoad;
    }

    /**
     * Phiếu kiểm hỏng sẽ được chuyển đến account 8,9,12 và phân xưởng lập kiểm hỏng trong VĂN BẢN ĐẾN
     */
    private void guiVanBanDen() {
        try {
            List<VanBanDen> contents = userRepository.findByGroup(group_12_PLUS).stream()
                    .filter(el -> el.getLevel() == 3)
                    .map(el -> {
                        VanBanDen vanBanDen = new VanBanDen();
                        vanBanDen.setNoiDung(vbdKiemHong + "ngày: " + DateTimeUtils.nowAsString());
                        vanBanDen.setNoiNhan(el.getUserId());
                        return vanBanDen;
                    })
                    .collect(Collectors.toList());

            vanBanDenRepository.saveAll(contents);
        } catch (Exception e) {
            logger.error("[Kiem hong] Can't save Van Ban Den");
        }
    }

    /**
     * Phai dung permission khi xac nhan
     * Khi Chuyen thi phai co xac nhan, xac nhan thi phai co chuyen
     */
    private void validateXacNhan(Long userId, KiemHong requestKiemHong, KiemHong existedKiemHong) {
//        if ((requestKiemHong.getTroLyKTXacNhan() || requestKiemHong.getQuanDocXacNhan() || requestKiemHong.getToTruongXacNhan()) && requestKiemHong.getNoiNhan() == null) {
//            throw new PXException("noi_nhan.must_choose");
//        }
//        if ((!requestKiemHong.getTroLyKTXacNhan() && !requestKiemHong.getQuanDocXacNhan() && !requestKiemHong.getToTruongXacNhan()) && requestKiemHong.getNoiNhan() != null) {
//            throw new PXException("Phải có người xác nhận");
//        }
        User user = userService.findById(userId);
        if (user.isToTruong() && (requestKiemHong.getQuanDocXacNhan() || requestKiemHong.getTroLyKTXacNhan())) {
            requestKiemHong.setQuanDocXacNhan(existedKiemHong.getQuanDocXacNhan());
            requestKiemHong.setTroLyKTXacNhan(existedKiemHong.getTroLyKTXacNhan());
        }
        if (user.isTroLyKT() && (requestKiemHong.getQuanDocXacNhan() || requestKiemHong.getToTruongXacNhan())) {
            requestKiemHong.setQuanDocXacNhan(existedKiemHong.getQuanDocXacNhan());
            requestKiemHong.setToTruongXacNhan(existedKiemHong.getToTruongXacNhan());
        }
        if (user.isQuanDocPhanXuong() && (requestKiemHong.getToTruongXacNhan() || requestKiemHong.getTroLyKTXacNhan())) {
            requestKiemHong.setToTruongXacNhan(existedKiemHong.getToTruongXacNhan());
            requestKiemHong.setTroLyKTXacNhan(existedKiemHong.getTroLyKTXacNhan());
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

    public void cleanKiemHongDetails(KiemHong requestKiemHong, KiemHong existedKiemHong) {
        try {
            if (Objects.isNull(existedKiemHong)) {
                return;
            }
            Set<Long> requestDetailIds = requestKiemHong.getKiemHongDetails()
                    .stream()
                    .map(el -> el.getKhDetailId())
                    .collect(Collectors.toSet());

            Set<Long> kiemHongDetailIds = existedKiemHong.getKiemHongDetails()
                    .stream()
                    .map(KiemHongDetail::getKhDetailId)
                    .filter(el -> !requestDetailIds.contains(el))
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
