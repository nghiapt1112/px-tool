package com.px.tool.domain.phuongan.service.impl;

import com.px.tool.domain.RequestType;
import com.px.tool.domain.cntp.CongNhanThanhPham;
import com.px.tool.domain.cntp.repository.CongNhanThanhPhamRepository;
import com.px.tool.domain.phuongan.DinhMucLaoDong;
import com.px.tool.domain.phuongan.DinhMucVatTu;
import com.px.tool.domain.phuongan.PhuongAn;
import com.px.tool.domain.phuongan.PhuongAnPayload;
import com.px.tool.domain.phuongan.repository.DinhMucLaoDongRepository;
import com.px.tool.domain.phuongan.repository.DinhMucVatTuRepository;
import com.px.tool.domain.phuongan.repository.PhuongAnRepository;
import com.px.tool.domain.phuongan.service.PhuongAnService;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.service.RequestService;
import com.px.tool.domain.user.User;
import com.px.tool.domain.user.service.UserService;
import com.px.tool.infrastructure.utils.DateTimeUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PhuongAnServiceImpl implements PhuongAnService {
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

    @Override
    public PhuongAnPayload findById(Long userId, Long id) {
        Request request = requestService.findById(id);
        PhuongAnPayload payload = PhuongAnPayload.fromEntity(request.getPhuongAn())
                .filterPermission(userService.findById(userId));;;
        payload.setRequestId(request.getRequestId());
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
        Long phuongAnReceiverId = existedPhuongAn.getRequest().getPhuongAnReceiverId();
        Long cntpReceiverId = existedPhuongAn.getRequest().getCntpReceiverId();

        Long requestId = existedPhuongAn.getRequest().getRequestId();
        CongNhanThanhPham thanhPham = existedPhuongAn.getRequest().getCongNhanThanhPham();
        PhuongAn phuongAn = new PhuongAn();
        phuongAnPayload.toEntity(phuongAn);
        validateXacNhan(requestId, phuongAn, existedPhuongAn);
        capNhatNgayThangChuKy(phuongAn, existedPhuongAn);
        if (phuongAn.allApproved()) {
            existedPhuongAn.getRequest().setStatus(RequestType.CONG_NHAN_THANH_PHAM);
            phuongAn.setRequest(existedPhuongAn.getRequest());
            taoCNTP(phuongAn, thanhPham);
            cntpReceiverId = phuongAnPayload.getNoiNhan();
        }
        cleanOldDetailData(phuongAn, existedPhuongAn);
        requestService.updateReceiveId(requestId, kiemHongReceiverId, phieuDatHangReceiverId, phuongAnReceiverId, cntpReceiverId);
        return phuongAnRepository.save(phuongAn);
    }

    private void taoCNTP(PhuongAn phuongAn, CongNhanThanhPham congNhanThanhPham) {
//        CongNhanThanhPham congNhanThanhPham = new CongNhanThanhPham();
        congNhanThanhPham.setTenSanPham(phuongAn.getSanPham());
        congNhanThanhPham.setNoiDung(phuongAn.getNoiDung());
        congNhanThanhPham.setSoPA(phuongAn.getMaSo());
        congNhanThanhPham.setDonviThucHien("neu chuyen ve px 3 hien px 3"); // TODO : set ten phan xuong vao day

        congNhanThanhPhamRepository.save(congNhanThanhPham);
    }

    /**
     * Phai dung permission khi xac nhan
     * Khi Chuyen thi phai co xac nhan, xac nhan thi phai co chuyen
     */
    private void validateXacNhan(Long userId, PhuongAn phuongAn, PhuongAn existedPhuongAn) {
        User user = userService.findById(userId);
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
        if (existedPhuongAn.getTruongPhongVatTuXacNhan() != existedPhuongAn.getTruongPhongVatTuXacNhan()) {
            phuongAn.setNgayThangNamtpVatTu(DateTimeUtils.nowAsString());
        }
        if (existedPhuongAn.getTruongPhongKeHoachXacNhan() != existedPhuongAn.getTruongPhongKeHoachXacNhan()) {
            phuongAn.setNgayThangNamTPKEHOACH(DateTimeUtils.nowAsString());
        }
        if (existedPhuongAn.getNguoiLapXacNhan() != existedPhuongAn.getNguoiLapXacNhan()) {
            phuongAn.setNgayThangNamNguoiLap(DateTimeUtils.nowAsString());
        }
        if (existedPhuongAn.getTruongPhongKTHKXacNhan() != existedPhuongAn.getTruongPhongKTHKXacNhan()) {
            phuongAn.setNgayThangNamTPKTHK(DateTimeUtils.nowAsString());
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
}
