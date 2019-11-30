package com.px.tool.domain.phuongan.service.impl;

import com.px.tool.domain.RequestType;
import com.px.tool.domain.cntp.CongNhanThanhPham;
import com.px.tool.domain.cntp.NoiDungThucHien;
import com.px.tool.domain.cntp.repository.CongNhanThanhPhamRepository;
import com.px.tool.domain.file.FileStorageService;
import com.px.tool.domain.phuongan.DinhMucLaoDong;
import com.px.tool.domain.phuongan.DinhMucVatTu;
import com.px.tool.domain.phuongan.PhuongAn;
import com.px.tool.domain.phuongan.PhuongAnPayload;
import com.px.tool.domain.phuongan.repository.DinhMucLaoDongRepository;
import com.px.tool.domain.phuongan.repository.DinhMucVatTuRepository;
import com.px.tool.domain.phuongan.repository.PhuongAnRepository;
import com.px.tool.domain.phuongan.service.PhuongAnService;
import com.px.tool.domain.request.NguoiDangXuLy;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.repository.RequestRepository;
import com.px.tool.domain.request.service.RequestService;
import com.px.tool.domain.user.User;
import com.px.tool.domain.user.service.UserService;
import com.px.tool.domain.vanbanden.service.VanBanDenServiceImpl;
import com.px.tool.infrastructure.utils.DateTimeUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.px.tool.domain.user.repository.UserRepository.group_cac_truong_phong;

@Service
public class PhuongAnServiceImpl implements PhuongAnService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PhuongAnRepository phuongAnRepository;

    @Autowired
    private RequestService requestService;

    @Autowired
    private DinhMucVatTuRepository dinhMucVatTuRepository;

    @Autowired
    private DinhMucLaoDongRepository dinhMucLaoDongRepository;

    @Autowired
    private CongNhanThanhPhamRepository congNhanThanhPhamRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private VanBanDenServiceImpl vanBanDenService;

    @Autowired
    private RequestRepository requestRepository;

    @Override
    public PhuongAnPayload findById(Long userId, Long id) {
        Request request = requestService.findById(id);

        PhuongAnPayload payload = PhuongAnPayload.fromEntity(request.getPhuongAn())
                .filterPermission(userService.findById(userId))
                .withFiles(fileStorageService.listFileNames(RequestType.PHUONG_AN, id));
        payload.setRequestId(request.getRequestId());

        List<Long> signedIds = new ArrayList<>(3);

        if (payload.getGiamDocXacNhan()) {
            signedIds.add(payload.getGiamDocId());
        }
        if (payload.getTruongPhongKTHKXacNhan()) {
            signedIds.add(payload.getTruongPhongKTHKId());
        }
        if (payload.getTruongPhongKeHoachXacNhan()) {
            signedIds.add(payload.getTruongPhongKeHoachId());
        }
        if (payload.getTruongPhongVatTuXacNhan()) {
            signedIds.add(payload.getTruongPhongVatTuId());
        }
        if (payload.getNguoiLapXacNhan()) {
            signedIds.add(payload.getNguoiLapId());
        }

        if (CollectionUtils.isEmpty(signedIds)) {
            return payload;
        }
        for (User user : userService.findByIds(signedIds)) {
            if (payload.getGiamDocXacNhan() && user.getUserId().equals(payload.getGiamDocId())) {
                payload.setGiamDocSignImg(user.getSignImg());
                payload.setGiamDocFullName(user.getFullName());
            }
            if (payload.getTruongPhongKTHKXacNhan() && user.getUserId().equals(payload.getTruongPhongKTHKId())) {
                payload.setTruongPhongKTHKSignImg(user.getSignImg());
                payload.setTruongPhongKTHKFullName(user.getFullName());
            }
            if (payload.getTruongPhongKeHoachXacNhan() && user.getUserId().equals(payload.getTruongPhongKeHoachId())) {
                payload.setTruongPhongKeHoachSignImg(user.getSignImg());
                payload.setTruongPhongKeHoachFullName(user.getFullName());
            }
            if (payload.getTruongPhongVatTuXacNhan() && user.getUserId().equals(payload.getTruongPhongVatTuId())) {
                payload.setTruongPhongVatTuSignImg(user.getSignImg());
                payload.setTruongPhongVatTuFullName(user.getFullName());
            }
            if (payload.getNguoiLapXacNhan() && user.getUserId().equals(payload.getNguoiLapId())) {
                payload.setNguoiLapSignImg(user.getSignImg());
                payload.setNguoiLapFullName(user.getFullName());
            }

            if (CollectionUtils.isEmpty(signedIds)) {
                return payload;
            }
        }
        return payload;
    }

    @Override
    public List<PhuongAn> findByPhongBan(Long userId) {
        return null;
    }

    @Override
    @Transactional
    public PhuongAn save(Long userId, PhuongAnPayload phuongAnPayload) {
        if (Objects.isNull(phuongAnPayload.getPaId())) {
            throw new RuntimeException("Phuong an phai co id");
        }
        PhuongAn existedPhuongAn = phuongAnRepository
                .findById(phuongAnPayload.getPaId())
                .orElse(null);


        Long kiemHongReceiverId = existedPhuongAn.getRequest().getKiemHongReceiverId();
        Long phieuDatHangReceiverId = existedPhuongAn.getRequest().getPhieuDatHangReceiverId();
        Long phuongAnReceiverId = Objects.isNull(phuongAnPayload.getNoiNhan()) ? userId : phuongAnPayload.getNoiNhan();
        Long cntpReceiverId = existedPhuongAn.getRequest().getCntpReceiverId();

        User user = userService.findById(userId);
        phuongAnPayload.capNhatChuKy(user);
        Long requestId = existedPhuongAn.getRequest().getRequestId();
        CongNhanThanhPham thanhPham = existedPhuongAn.getRequest().getCongNhanThanhPham();
        PhuongAn phuongAn = new PhuongAn();
        phuongAnPayload.toEntity(phuongAn);

        validateXacNhan(user, phuongAn, existedPhuongAn);
        capNhatNgayThangChuKy(phuongAn, existedPhuongAn);
        if (phuongAn.allApproved()) {
            existedPhuongAn.getRequest().setStatus(RequestType.CONG_NHAN_THANH_PHAM);
            phuongAn.setRequest(existedPhuongAn.getRequest());
            taoCNTP(phuongAn, thanhPham);
            guiVanBanDen();
            // clear receiverId
            cntpReceiverId = phuongAnPayload.getNoiNhan();
            phuongAnReceiverId = null;
            phieuDatHangReceiverId = null;
            kiemHongReceiverId = null;

        }
        cleanOldDetailData(phuongAn, existedPhuongAn);
        requestService.updateReceiveId(requestId, kiemHongReceiverId, phieuDatHangReceiverId, phuongAnReceiverId, cntpReceiverId);
        return phuongAnRepository.save(phuongAn);
    }

    private void guiVanBanDen() {
        vanBanDenService.guiVanBanDen(group_cac_truong_phong, RequestType.PHUONG_AN);
    }

    private void taoCNTP(PhuongAn phuongAn, CongNhanThanhPham congNhanThanhPham) {
        congNhanThanhPham.setTenSanPham(phuongAn.getSanPham());
        congNhanThanhPham.setNoiDung(phuongAn.getNoiDung());
        congNhanThanhPham.setSoPA(phuongAn.getMaSo());
        if (!CollectionUtils.isEmpty(phuongAn.getDinhMucLaoDongs())) {
            Set<NoiDungThucHien> noiDungThucHiens = new HashSet<>();
            for (DinhMucLaoDong dinhMucLaoDong : phuongAn.getDinhMucLaoDongs()) {
                noiDungThucHiens.add(new NoiDungThucHien(dinhMucLaoDong.getNoiDungCongViec()));
            }
            congNhanThanhPham.setNoiDungThucHiens(noiDungThucHiens);
        }
        congNhanThanhPhamRepository.save(congNhanThanhPham);
    }

    /**
     * Phai dung permission khi xac nhan
     * Khi Chuyen thi phai co xac nhan, xac nhan thi phai co chuyen
     */
    private void validateXacNhan(User user, PhuongAn phuongAn, PhuongAn existedPhuongAn) {

        if (user.isNguoiLapPhieu()) {
            phuongAn.setTruongPhongVatTuXacNhan(existedPhuongAn.getTruongPhongVatTuXacNhan());
            phuongAn.setTruongPhongKeHoachXacNhan(existedPhuongAn.getTruongPhongKeHoachXacNhan());
            phuongAn.setTruongPhongKTHKXacNhan(existedPhuongAn.getTruongPhongKTHKXacNhan());
        }
        if (user.isTruongPhongVatTu()) {
            phuongAn.setTruongPhongKeHoachXacNhan(existedPhuongAn.getTruongPhongKeHoachXacNhan());
            phuongAn.setTruongPhongKTHKXacNhan(existedPhuongAn.getTruongPhongKTHKXacNhan());
            phuongAn.setNguoiLapXacNhan(existedPhuongAn.getNguoiLapXacNhan());
        }
        if (user.isTruongPhongKeHoach()) {
            phuongAn.setTruongPhongVatTuXacNhan(existedPhuongAn.getTruongPhongVatTuXacNhan());
            phuongAn.setTruongPhongKTHKXacNhan(existedPhuongAn.getTruongPhongKTHKXacNhan());
            phuongAn.setNguoiLapXacNhan(existedPhuongAn.getNguoiLapXacNhan());
        }
        if (user.isTruongPhongKTHK()) {
            phuongAn.setTruongPhongVatTuXacNhan(existedPhuongAn.getTruongPhongVatTuXacNhan());
            phuongAn.setTruongPhongKeHoachXacNhan(existedPhuongAn.getTruongPhongKeHoachXacNhan());
            phuongAn.setNguoiLapXacNhan(existedPhuongAn.getNguoiLapXacNhan());
        }

    }

    /**
     * khi co xac nhan thi cap nhat ngay thang
     *
     * @param phuongAn
     * @param existedPhuongAn
     */
    private void capNhatNgayThangChuKy(PhuongAn phuongAn, PhuongAn existedPhuongAn) {
        if (phuongAn.getTruongPhongVatTuXacNhan() != existedPhuongAn.getTruongPhongVatTuXacNhan()) {
            phuongAn.setNgayThangNamtpVatTu(DateTimeUtils.nowAsString());
        }
        if (phuongAn.getTruongPhongKeHoachXacNhan() != existedPhuongAn.getTruongPhongKeHoachXacNhan()) {
            phuongAn.setNgayThangNamTPKEHOACH(DateTimeUtils.nowAsString());
        }
        if (phuongAn.getNguoiLapXacNhan() != existedPhuongAn.getNguoiLapXacNhan()) {
            phuongAn.setNgayThangNamNguoiLap(DateTimeUtils.nowAsString());
        }
        if (phuongAn.getTruongPhongKTHKXacNhan() != existedPhuongAn.getTruongPhongKTHKXacNhan()) {
            phuongAn.setNgayThangNamTPKTHK(DateTimeUtils.nowAsString());
        }
        if (phuongAn.getGiamDocXacNhan() != existedPhuongAn.getGiamDocXacNhan()) {
            phuongAn.setNgayThangNamGiamDoc(DateTimeUtils.nowAsString());
        }
    }

    private void cleanOldDetailData(PhuongAn requestPhuongAn, PhuongAn existedPhuongAn) {
        try {
            if (Objects.isNull(existedPhuongAn)) {
                return;
            }

            Set<Long> requsetDinhMucVatTuIds = requestPhuongAn.getDinhMucVatTus()
                    .stream()
                    .map(DinhMucVatTu::getVtId)
                    .collect(Collectors.toSet());

            Set<Long> vatuIds = existedPhuongAn.getDinhMucVatTus()
                    .stream()
                    .map(DinhMucVatTu::getVtId)
                    .filter(el -> !requsetDinhMucVatTuIds.contains(el))
                    .collect(Collectors.toSet());

            Set<Long> requsetLaoDongIds = requestPhuongAn.getDinhMucLaoDongs()
                    .stream()
                    .map(DinhMucLaoDong::getDmId)
                    .collect(Collectors.toSet());

            Set<Long> laoDongIds = existedPhuongAn.getDinhMucLaoDongs()
                    .stream()
                    .map(DinhMucLaoDong::getDmId)
                    .filter(el -> !requsetLaoDongIds.contains(el))
                    .collect(Collectors.toSet());
            if (!CollectionUtils.isEmpty(vatuIds)) {
                dinhMucVatTuRepository.deleteAllByIds(vatuIds);
            }
            if (!CollectionUtils.isEmpty(laoDongIds)) {
                dinhMucLaoDongRepository.deleteAllByIds(laoDongIds);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Co loi xay ra trong qua trinh clean DinhMucVatTu, DinhMucLaoDong");
        }
    }

    @Override
    public PhuongAn savePhuongAn(PhuongAn phuongAn) {
        return this.phuongAnRepository.save(phuongAn);
    }

    @Override
    public NguoiDangXuLy findNguoiDangXuLy(Long requestId) {
        Long phuongAnId = requestRepository.findPhuongAnId(requestId);
        return phuongAnRepository.findDetail(phuongAnId);
    }
}

