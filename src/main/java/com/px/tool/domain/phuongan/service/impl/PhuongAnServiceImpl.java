package com.px.tool.domain.phuongan.service.impl;

import com.px.tool.domain.RequestType;
import com.px.tool.domain.cntp.CongNhanThanhPham;
import com.px.tool.domain.cntp.NoiDungThucHien;
import com.px.tool.domain.cntp.repository.CongNhanThanhPhamRepository;
import com.px.tool.domain.file.FileStorageService;
import com.px.tool.domain.kiemhong.repository.KiemHongDetailRepository;
import com.px.tool.domain.phuongan.DinhMucLaoDong;
import com.px.tool.domain.phuongan.DinhMucVatTu;
import com.px.tool.domain.phuongan.PhuongAn;
import com.px.tool.domain.phuongan.PhuongAnPayload;
import com.px.tool.domain.phuongan.PhuongAnTaoMoi;
import com.px.tool.domain.phuongan.RequestTaoPhuongAnMoi;
import com.px.tool.domain.phuongan.repository.DinhMucLaoDongRepository;
import com.px.tool.domain.phuongan.repository.DinhMucVatTuRepository;
import com.px.tool.domain.phuongan.repository.PhuongAnRepository;
import com.px.tool.domain.phuongan.service.PhuongAnService;
import com.px.tool.domain.request.NguoiDangXuLy;
import com.px.tool.domain.request.repository.RequestRepository;
import com.px.tool.domain.request.service.RequestService;
import com.px.tool.domain.user.User;
import com.px.tool.domain.user.service.UserService;
import com.px.tool.domain.vanbanden.VanBanDen;
import com.px.tool.domain.vanbanden.repository.VanBanDenRepository;
import com.px.tool.domain.vanbanden.service.VanBanDenServiceImpl;
import com.px.tool.infrastructure.exception.PXException;
import com.px.tool.infrastructure.utils.DateTimeUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
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

    @Autowired
    private KiemHongDetailRepository kiemHongDetailRepository;

    @Autowired
    private VanBanDenRepository vanBanDenRepository;

    @Override
    public PhuongAnPayload findById(Long userId, Long id) {
//        Request request = requestService.findById(id);

        PhuongAn pa = phuongAnRepository.findById(id).orElseThrow(() -> new PXException("phuongan.not_found"));
        PhuongAnPayload payload = PhuongAnPayload.fromEntity(pa)
                .filterPermission(userService.findById(userId))
                .withFiles(fileStorageService.listFileNames(RequestType.PHUONG_AN, id));
//        payload.setRequestId(request.getRequestId());// code cu
        payload.setRequestId(pa.getPaId());

        payload.processSignImgAndFullName(userService.userById());
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


        Long phuongAnReceiverId = Objects.isNull(phuongAnPayload.getNoiNhan()) ? userId : phuongAnPayload.getNoiNhan();
        Long cntpReceiverId = existedPhuongAn.getCntpReceiverId();

        User user = userService.findById(userId);
        phuongAnPayload.capNhatChuKy(user);

        CongNhanThanhPham thanhPham = existedPhuongAn.getCongNhanThanhPham();
        PhuongAn phuongAn = new PhuongAn();
        phuongAnPayload.toEntity(phuongAn);
        phuongAnPayload.capNhatNgayThangChuKy(phuongAn, existedPhuongAn);

//        validateXacNhan(user, phuongAn, existedPhuongAn);
//        capNhatNgayThangChuKy(phuongAn, existedPhuongAn);
        if (phuongAn.allApproved()) {
            existedPhuongAn.setStatus(RequestType.CONG_NHAN_THANH_PHAM);
            taoCNTP(phuongAn, thanhPham);
            guiVanBanDen(phuongAnPayload);
            // clear receiverId
            cntpReceiverId = phuongAnPayload.getNoiNhan();
            phuongAnReceiverId = null;

        }
        cleanOldDetailData(phuongAn, existedPhuongAn);
        phuongAn.setPhuongAnReceiverId(phuongAnReceiverId);
        phuongAn.setCntpReceiverId(cntpReceiverId);
        return phuongAnRepository.save(phuongAn);
    }

    private void guiVanBanDen(PhuongAnPayload phuongAnPayload) {
        try {
            List<VanBanDen> contents = phuongAnPayload.getCusReceivers().stream()
                    .map(el -> {
                        VanBanDen vanBanDen = new VanBanDen();
                        vanBanDen.setNoiDung(phuongAnPayload.getNoiDung());

                        vanBanDen.setNoiNhan(el);
                        vanBanDen.setRequestType(RequestType.PHUONG_AN);
                        vanBanDen.setRead(false);
                        return vanBanDen;
                    })
                    .collect(Collectors.toList());
            vanBanDenRepository.saveAll(contents);
        } catch (Exception e) {
            throw new PXException("[Phương Án]: Có lỗi khi gửi văn bản đến.");
        }
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
//    private void validateXacNhan(User user, PhuongAn phuongAn, PhuongAn existedPhuongAn) {
//
//        if (user.isNguoiLapPhieu()) {
//            phuongAn.setTruongPhongVatTuXacNhan(existedPhuongAn.getTruongPhongVatTuXacNhan());
//            phuongAn.setTruongPhongKeHoachXacNhan(existedPhuongAn.getTruongPhongKeHoachXacNhan());
//            phuongAn.setTruongPhongKTHKXacNhan(existedPhuongAn.getTruongPhongKTHKXacNhan());
//        }
//        if (user.isTruongPhongVatTu()) {
//            phuongAn.setTruongPhongKeHoachXacNhan(existedPhuongAn.getTruongPhongKeHoachXacNhan());
//            phuongAn.setTruongPhongKTHKXacNhan(existedPhuongAn.getTruongPhongKTHKXacNhan());
//            phuongAn.setNguoiLapXacNhan(existedPhuongAn.getNguoiLapXacNhan());
//        }
//        if (user.isTruongPhongKeHoach()) {
//            phuongAn.setTruongPhongVatTuXacNhan(existedPhuongAn.getTruongPhongVatTuXacNhan());
//            phuongAn.setTruongPhongKTHKXacNhan(existedPhuongAn.getTruongPhongKTHKXacNhan());
//            phuongAn.setNguoiLapXacNhan(existedPhuongAn.getNguoiLapXacNhan());
//        }
//        if (user.isTruongPhongKTHK()) {
//            phuongAn.setTruongPhongVatTuXacNhan(existedPhuongAn.getTruongPhongVatTuXacNhan());
//            phuongAn.setTruongPhongKeHoachXacNhan(existedPhuongAn.getTruongPhongKeHoachXacNhan());
//            phuongAn.setNguoiLapXacNhan(existedPhuongAn.getNguoiLapXacNhan());
//        }
//
//    }

    /**
     * khi co xac nhan thi cap nhat ngay thang
     *
     * @param phuongAn
     * @param existedPhuongAn
     */
//    private void capNhatNgayThangChuKy(PhuongAn phuongAn, PhuongAn existedPhuongAn) {
//        if (phuongAn.getTruongPhongVatTuXacNhan() != existedPhuongAn.getTruongPhongVatTuXacNhan()) {
//            phuongAn.setNgayThangNamtpVatTu(DateTimeUtils.nowAsString());
//        }
//        if (phuongAn.getTruongPhongKeHoachXacNhan() != existedPhuongAn.getTruongPhongKeHoachXacNhan()) {
//            phuongAn.setNgayThangNamTPKEHOACH(DateTimeUtils.nowAsString());
//        }
//        if (phuongAn.getNguoiLapXacNhan() != existedPhuongAn.getNguoiLapXacNhan()) {
//            phuongAn.setNgayThangNamNguoiLap(DateTimeUtils.nowAsString());
//        }
//        if (phuongAn.getTruongPhongKTHKXacNhan() != existedPhuongAn.getTruongPhongKTHKXacNhan()) {
//            phuongAn.setNgayThangNamTPKTHK(DateTimeUtils.nowAsString());
//        }
//        if (phuongAn.getGiamDocXacNhan() != existedPhuongAn.getGiamDocXacNhan()) {
//            phuongAn.setNgayThangNamGiamDoc(DateTimeUtils.nowAsString());
//        }
//    }

    private void cleanOldDetailData(PhuongAn requestPhuongAn, PhuongAn existedPhuongAn) {
        try {
            if (Objects.isNull(requestPhuongAn) || Objects.isNull(existedPhuongAn)) {
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
        return phuongAnRepository.findDetail(requestId);
    }

    @Override
    public PhuongAnTaoMoi taoPhuongAnMoi(RequestTaoPhuongAnMoi requestTaoPhuongAnMoi) {
        PhuongAn phuongAn = taoPhuongAnMoi();
        CongNhanThanhPham cntp = congNhanThanhPhamRepository.save(new CongNhanThanhPham());
        kiemHongDetailRepository.taoPhuongAn(phuongAn.getPaId(), requestTaoPhuongAnMoi.getDetailIds());
        PhuongAnTaoMoi paMoi = new PhuongAnTaoMoi();
        paMoi.setPaId(phuongAn.getPaId());

        phuongAnRepository.updateCNTP(phuongAn.getPaId(), cntp.getTpId());
        return paMoi;
    }

    @Transactional
    public PhuongAn taoPhuongAnMoi() {
        return phuongAnRepository.save(new PhuongAn());
    }

    @Override
    public Map<Long, PhuongAn> groupById(List<Long> paIds) {
        List<PhuongAn> list = phuongAnRepository.findAllById(paIds);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        } else {
            return list
                    .stream()
                    .collect(Collectors.toMap(PhuongAn::getPaId, Function.identity()));
        }

    }

    @Override
    public List<PhuongAn> findByUserId(Long userId) {
        return phuongAnRepository.findByUserId(userId);
    }
}

