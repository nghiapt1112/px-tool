package com.px.tool.domain.request.service.impl;

import com.px.tool.domain.request.payload.DashBoardCongViecCuaToi;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.payload.PageDashBoardCongViecCuaToi;
import com.px.tool.domain.request.payload.ThongKeDetailPayload;
import com.px.tool.domain.request.payload.PageThongKePayload;
import com.px.tool.domain.request.payload.ThongKeRequest;
import com.px.tool.domain.request.repository.RequestRepository;
import com.px.tool.domain.request.service.RequestService;
import com.px.tool.domain.user.User;
import com.px.tool.domain.user.repository.UserRepository;
import com.px.tool.domain.user.service.UserService;
import com.px.tool.infrastructure.exception.PXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
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
    public PageDashBoardCongViecCuaToi timByNguoiNhan(Long userId, com.px.tool.domain.request.payload.PageRequest pageRequest) {
        logger.info("Finding cong viec can xu ly with userId: {}", userId);
        User currentUser = userService.findById(userId);
        Map<Long, User> userById = userService.userById();
        Page<Request> requestsByNguoiGui = null;
        if (currentUser.getLevel() == 3) {
            List<User> members = userRepository.findByGroup(Arrays.asList(currentUser.getPhongBan().getPhongBanId()));
            if (CollectionUtils.isEmpty(members)) {
                throw new PXException("member.not_found");
            }
            requestsByNguoiGui = requestRepository.findByNguoiNhan(
                    members.stream().map(User::getUserId).collect(Collectors.toSet()), // list toan bo danh sach cua 1 team
                    PageRequest.of(pageRequest.getPage(), pageRequest.getSize())
            );
        } else {
            requestsByNguoiGui = requestRepository.findByNguoiNhan(Arrays.asList(userId), PageRequest.of(pageRequest.getPage(), pageRequest.getSize()));
        }
        PageDashBoardCongViecCuaToi pageDashBoardCongViecCuaToi = new PageDashBoardCongViecCuaToi();
        pageDashBoardCongViecCuaToi.setDetails(requestsByNguoiGui
                .stream()
                .map(el -> DashBoardCongViecCuaToi.fromEntity(el, userById))
                .collect(Collectors.toList()));
        pageDashBoardCongViecCuaToi.setTotal(requestsByNguoiGui.getTotalPages());
        pageDashBoardCongViecCuaToi.setPage(pageRequest.getPage());
        pageDashBoardCongViecCuaToi.setSize(pageRequest.getSize());
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
    public PageThongKePayload collectDataThongKe(ThongKeRequest request) {
        Page<Request> requests = requestRepository.findPaging(request, PageRequest.of(request.getPage(),request.getSize()));
        PageThongKePayload tkPayload = new PageThongKePayload();
        tkPayload.setSanPham("");
        tkPayload.setTienDo("0");
        tkPayload.setDetails(requests.stream()
                .map(ThongKeDetailPayload::fromRequestEntity)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
        tkPayload.setTotal(requests.getTotalPages());
        tkPayload.setSize(request.getSize());
        tkPayload.setPage(request.getPage());
        return tkPayload;
    }

    @Override
    public void updateReceiveId(Long requestId, Long kiemHongReceiverId, Long phieuDatHangReceiverId, Long phuongAnReceiverId, Long cntpReceiverId) {
        requestRepository.updateReceiverId(requestId, kiemHongReceiverId, phieuDatHangReceiverId, phuongAnReceiverId, cntpReceiverId);
    }
}
