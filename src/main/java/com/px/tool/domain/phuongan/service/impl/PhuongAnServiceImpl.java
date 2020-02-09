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
import com.px.tool.domain.user.User;
import com.px.tool.domain.user.service.UserService;
import com.px.tool.domain.vanbanden.VanBanDen;
import com.px.tool.domain.vanbanden.repository.VanBanDenRepository;
import com.px.tool.domain.vanbanden.service.VanBanDenServiceImpl;
import com.px.tool.infrastructure.exception.PXException;
import com.px.tool.infrastructure.utils.CommonUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private PhuongAnRepository phuongAnRepository;

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
        phuongAnPayload.validateXacNhan(user, phuongAn, existedPhuongAn);

        if (phuongAn.allApproved()) {
            phuongAn.setStep(1L);
            existedPhuongAn.setStatus(RequestType.CONG_NHAN_THANH_PHAM);
            taoCNTP(phuongAn, thanhPham);
            phuongAn.setCongNhanThanhPham(thanhPham);
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

    @Transactional
    public void guiVanBanDen(PhuongAnPayload phuongAnPayload) {
        try {
            VanBanDen vanBanDen = new VanBanDen();
            vanBanDen.setNoiDung(phuongAnPayload.getCusNoiDung());
            vanBanDen.setNoiNhan(CommonUtils.toString(phuongAnPayload.getCusReceivers()));
            vanBanDen.setRequestType(RequestType.PHUONG_AN);
            vanBanDen.setRead(false);
            vanBanDen.setSoPa("Phương Án số: " + phuongAnPayload.getPaId());
            vanBanDen.setRequestId(phuongAnPayload.getRequestId());
            vanBanDenRepository.save(vanBanDen);
        } catch (Exception e) {
            throw new PXException("[Phương Án]: Có lỗi khi gửi văn bản đến.");
        }
    }

    @Transactional
    public void taoCNTP(PhuongAn phuongAn, CongNhanThanhPham congNhanThanhPham) {
        if (congNhanThanhPham == null) {
            congNhanThanhPham = new CongNhanThanhPham();
            congNhanThanhPham.setPhuongAn(phuongAn);
        }
        congNhanThanhPham.setTenSanPham(phuongAn.getSanPham());
        congNhanThanhPham.setNoiDung(phuongAn.getNoiDung());
        congNhanThanhPham.setSoPA(phuongAn.getMaSo());
        if (!CollectionUtils.isEmpty(phuongAn.getDinhMucLaoDongs())) {
            Set<NoiDungThucHien> noiDungThucHiens = new HashSet<>();
            NoiDungThucHien detail = null;
            for (DinhMucLaoDong dinhMucLaoDong : phuongAn.getDinhMucLaoDongs()) {
                detail = new NoiDungThucHien(dinhMucLaoDong.getNoiDungCongViec());
                detail.setCongNhanThanhPham(congNhanThanhPham);
                noiDungThucHiens.add(detail);
            }
            congNhanThanhPham.setNoiDungThucHiens(noiDungThucHiens);
        }
        congNhanThanhPham.setQuanDocIds(phuongAn.getNguoiThucHien());
        congNhanThanhPhamRepository.save(congNhanThanhPham);
    }

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
    public NguoiDangXuLy findNguoiDangXuLy(Long requestId) {
        return phuongAnRepository.findDetail(requestId);
    }

    @Override
    @Transactional
    public PhuongAnTaoMoi taoPhuongAnMoi(Long userid, RequestTaoPhuongAnMoi requestTaoPhuongAnMoi) {
        PhuongAn phuongAn = taoPhuongAnMoi(userid);
        CongNhanThanhPham cntp = congNhanThanhPhamRepository.save(new CongNhanThanhPham());
        kiemHongDetailRepository.taoPhuongAn(phuongAn.getPaId(), requestTaoPhuongAnMoi.getDetailIds());
        PhuongAnTaoMoi paMoi = new PhuongAnTaoMoi();
        paMoi.setPaId(phuongAn.getPaId());

        phuongAnRepository.updateCNTP(phuongAn.getPaId(), cntp.getTpId());
        return paMoi;
    }

    @Transactional
    public PhuongAn taoPhuongAnMoi(Long userid) {
        PhuongAn pa = new PhuongAn();
        pa.setStep(0L);
        pa.setNguoiLapId(userid);
        return phuongAnRepository.save(pa);
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

