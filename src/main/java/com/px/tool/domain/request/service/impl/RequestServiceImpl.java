package com.px.tool.domain.request.service.impl;

import com.px.tool.domain.request.DashBoardCongViecCuaToi;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.ThongKePayload;
import com.px.tool.domain.request.repository.RequestRepository;
import com.px.tool.domain.request.service.RequestService;
import com.px.tool.domain.user.User;
import com.px.tool.domain.user.repository.UserRepository;
import com.px.tool.domain.user.service.UserService;
import com.px.tool.infrastructure.exception.PXException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Request save(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public List<DashBoardCongViecCuaToi> timByNguoiGui(Collection<Long> userIds) {
        List<Request> requestsByNguoiGui = requestRepository.findByNguoiGui(userIds);
        return requestsByNguoiGui
                .stream()
                .map(DashBoardCongViecCuaToi::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<DashBoardCongViecCuaToi> timByNguoiNhan(Long userId) {
        // TODO: if level 3 => nhan ca team
        // TODO: if level 4,5 => nhan theo userId

        User currentUser = userService.findById(userId);
        List<Request> requestsByNguoiGui = null;
        if (currentUser.getLevel() == 3) {
            List<User> members = userRepository.findByGroup(Arrays.asList(currentUser.getPhongBan().getPhongBanId()));
            if (CollectionUtils.isEmpty(members)) {
                throw new PXException("member.not_found");
            }
            requestsByNguoiGui = requestRepository.findByNguoiNhan(
                    members.stream().map(User::getUserId).collect(Collectors.toSet())
            );
        } else {
            requestsByNguoiGui = requestRepository.findByNguoiNhan(Arrays.asList(userId));
        }
        return requestsByNguoiGui
                .stream()
                .map(DashBoardCongViecCuaToi::fromEntity)
                .collect(Collectors.toList());
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
    public List<ThongKePayload> collectDataThongKe(Long userId) {
        List<Request> requests = requestRepository.findAll();
        List<ThongKePayload> tks = requests.stream()
                .map(ThongKePayload::fromRequestEntity)
                .collect(Collectors.toList());
        return IntStream.rangeClosed(1, 20)
                .mapToObj(el -> {
                    ThongKePayload tk = new ThongKePayload();
                    tk.tt = Long.valueOf(el);
                    tk.tenPhuKien = "Ten phi kien __ " + el;
                    tk.tenLinhKien = "Ten link kien " + el;
                    tk.kyHieu = "Ki hieu " + el;
                    tk.SL = 10L;
                    tk.dangHuHong = "Dang hu hong" + el;
                    tk.ngayKiemHong = "ngay kiem hong" + el;
                    tk.phuongPhapKhacPhuc = "phuong phap khac phuc" + el;
                    tk.ngayChuyenPhongVatTu = "11/12/2018";
                    tk.soPhieuDatHang = "so phieu dat hang" + el;
                    tk.ngayChuyenKT = "15/03/2018";
                    tk.soPA = "So phuong an" + el;
                    tk.ngayRaPA = "15/03/2018";
                    tk.ngayChuyenKH = "15/03/2018";
                    tk.ngayPheDuyet = "15/03/2018";
                    tk.ngayHoanThanh = "15/03/2018";
                    tk.xacNhanHoanThanh = "Da hoan thanh";
                    return tk;
                })
                .collect(Collectors.toCollection(() -> new ArrayList<>(20)));
    }

    @Override
    public void updateReceiveId(Long requestId, Long kiemHongReceiverId, Long phieuDatHangReceiverId, Long phuongAnReceiverId, Long cntpReceiverId) {
        requestRepository.updateReceiverId(requestId, kiemHongReceiverId, phieuDatHangReceiverId, phuongAnReceiverId, cntpReceiverId);
    }
}
