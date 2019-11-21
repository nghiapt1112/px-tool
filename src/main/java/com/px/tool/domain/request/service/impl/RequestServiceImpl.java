package com.px.tool.domain.request.service.impl;

import com.px.tool.domain.request.DashBoardCongViecCuaToi;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.ThongKeDetailPayload;
import com.px.tool.domain.request.ThongKePayload;
import com.px.tool.domain.request.repository.RequestRepository;
import com.px.tool.domain.request.service.RequestService;
import com.px.tool.domain.user.User;
import com.px.tool.domain.user.repository.UserRepository;
import com.px.tool.domain.user.service.UserService;
import com.px.tool.infrastructure.exception.PXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RequestServiceImpl implements RequestService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
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
        logger.info("Finding cong viec can xu ly with userId: {}", userId);
        User currentUser = userService.findById(userId);
        List<Request> requestsByNguoiGui = null;
        if (currentUser.getLevel() == 3) {
            List<User> members = userRepository.findByGroup(Arrays.asList(currentUser.getPhongBan().getPhongBanId()));
            if (CollectionUtils.isEmpty(members)) {
                throw new PXException("member.not_found");
            }
            requestsByNguoiGui = requestRepository.findByNguoiNhan(
                    members.stream().map(User::getUserId).collect(Collectors.toSet()) // list toan bo danh sach cua 1 team
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
    public ThongKePayload collectDataThongKe(Long userId) {
        List<Request> requests = requestRepository.findAll();
        ThongKePayload tkPayload = new ThongKePayload();
        tkPayload.setSanPham("");
        tkPayload.setTienDo("0");
        tkPayload.setDetails(requests.stream()
                .map(ThongKeDetailPayload::fromRequestEntity)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
        return tkPayload;
    }

    @Override
    public void updateReceiveId(Long requestId, Long kiemHongReceiverId, Long phieuDatHangReceiverId, Long phuongAnReceiverId, Long cntpReceiverId) {
        requestRepository.updateReceiverId(requestId, kiemHongReceiverId, phieuDatHangReceiverId, phuongAnReceiverId, cntpReceiverId);
    }
}
