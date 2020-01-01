package com.px.tool.domain.request.service.impl;

import com.px.tool.domain.cntp.repository.CongNhanThanhPhamRepository;
import com.px.tool.domain.dathang.PhieuDatHangDetail;
import com.px.tool.domain.kiemhong.KiemHongDetail;
import com.px.tool.domain.mucdich.sudung.MucDichSuDung;
import com.px.tool.domain.mucdich.sudung.repository.MucDichSuDungRepository;
import com.px.tool.domain.phuongan.PhuongAn;
import com.px.tool.domain.phuongan.service.PhuongAnService;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.payload.DashBoardCongViecCuaToi;
import com.px.tool.domain.request.payload.PageDashBoardCongViecCuaToi;
import com.px.tool.domain.request.payload.ThongKeDetailPayload;
import com.px.tool.domain.request.payload.ThongKePageRequest;
import com.px.tool.domain.request.payload.ThongKePageResponse;
import com.px.tool.domain.request.repository.RequestRepository;
import com.px.tool.domain.request.service.RequestService;
import com.px.tool.domain.user.User;
import com.px.tool.domain.user.repository.UserRepository;
import com.px.tool.domain.user.service.UserService;
import com.px.tool.infrastructure.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements RequestService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MucDichSuDungRepository mucDichSuDungRepository;

    @Autowired
    private CongNhanThanhPhamRepository congNhanThanhPhamRepository;

    @Autowired
    private PhuongAnService phuongAnService;

    @Override
    @Transactional
    public Request save(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public PageDashBoardCongViecCuaToi timVanBanCanGiaiQuyet(Long userId, com.px.tool.infrastructure.model.payload.PageRequest pageRequest) {
        logger.info("Finding cong viec can xu ly with userId: {}", userId);
        User currentUser = userService.findById(userId);
        Map<Long, User> userById = userService.userById();
        Page<Request> requestsByNguoiGui = requestRepository.findByNguoiNhan(Arrays.asList(userId), pageRequest.toPageRequest());

        PageDashBoardCongViecCuaToi pageDashBoardCongViecCuaToi = new PageDashBoardCongViecCuaToi(pageRequest.getPage(), pageRequest.getSize());
        pageDashBoardCongViecCuaToi.setDetails(requestsByNguoiGui
                .stream()
                .map(el -> DashBoardCongViecCuaToi.fromEntity(el, userById))
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));


        phuongAnService.findByUserId(userId)
                .stream()
                .map(el -> DashBoardCongViecCuaToi.fromPhuongAn(el, userById))
                .forEach(el -> pageDashBoardCongViecCuaToi.getDetails().add(el));

        // NOTE: truong hop la CNTP
        congNhanThanhPhamRepository.findAll(userId, userId.toString())
                .stream()
                .map(el -> DashBoardCongViecCuaToi.fromCNTP(el, userById))
                .forEach(el -> pageDashBoardCongViecCuaToi.getDetails().add(el));
        congNhanThanhPhamRepository.findAllTheoNhanVienKCS(userId)
                .stream()
                .map(el -> DashBoardCongViecCuaToi.fromCNTP(el, userById))
                .forEach(el -> pageDashBoardCongViecCuaToi.getDetails().add(el));

        return pageDashBoardCongViecCuaToi;
    }

    @Override
    public Request findById(Long id) {
        if (Objects.isNull(id)) {
            throw new RuntimeException("requestId khong duoc null");
        }
        return this.requestRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
    }

    @Override
    public ThongKePageResponse collectDataThongKe(ThongKePageRequest request) {
        Map<Long, String> mdsd = mucDichSuDungRepository.findAll()
                .stream()
                .collect(Collectors.toMap(MucDichSuDung::getMdId, MucDichSuDung::getTen));
        Page<Request> requests = requestRepository.findPaging(request, PageRequest.of(request.getPage(), request.getSize()));

        List<Long> paIds = new ArrayList<>();
        for (Request request1 : requests) {
            for (KiemHongDetail kiemHongDetail : request1.getKiemHong().getKiemHongDetails()) {
                if (kiemHongDetail.getPaId() != null) {
                    paIds.add(kiemHongDetail.getPaId());
                }
            }
        }
        Map<Long, PhuongAn> phuongAnById = phuongAnService.groupById(paIds);

        ThongKePageResponse tkPayload = new ThongKePageResponse(request.getPage(), request.getSize());
        tkPayload.setSanPham(mdsd.get(request.getSanPham()));
        AtomicReference<Integer> hoanThanhCount = new AtomicReference<>(0);
        tkPayload.setDetails(requests.stream()
                .map(el -> { // loc theo san pham
                    if (request.getSanPham() == null || request.getSanPham() == 0) {
                        return el;
                    }
                    Set<PhieuDatHangDetail> vals = new HashSet<>();
                    for (PhieuDatHangDetail phieuDatHangDetail : el.getPhieuDatHang().getPhieuDatHangDetails()) {
                        if (phieuDatHangDetail.getMucDichSuDung() != null && phieuDatHangDetail.getMucDichSuDung().equals(request.getSanPham())) {
                            vals.add(phieuDatHangDetail);
                        }
                    }
                    if (vals.size() == 0) {
                        return null;
                    } else {
                        el.getPhieuDatHang().setPhieuDatHangDetails(vals);
                        return el;
                    }
                })
                .filter(Objects::nonNull)
                .map(el -> ThongKeDetailPayload.fromRequestEntity(el, phuongAnById))
                .flatMap(el -> el.stream())
                .peek(el -> {
                    if (el.getXacNhanHoanThanh() != null) {
                        hoanThanhCount.getAndSet(hoanThanhCount.get() + 1);
                    }
                })
                .collect(Collectors.toList()));
        tkPayload.setTienDo(CommonUtils.getPercentage(hoanThanhCount.get(), tkPayload.getDetails().size()));
        return tkPayload;
    }

    @Override
    public void updateReceiveId(Long requestId, Long kiemHongReceiverId, Long phieuDatHangReceiverId, Long phuongAnReceiverId, Long cntpReceiverId) {
        requestRepository.updateReceiverId(requestId, kiemHongReceiverId, phieuDatHangReceiverId, phuongAnReceiverId, cntpReceiverId);
    }

}
