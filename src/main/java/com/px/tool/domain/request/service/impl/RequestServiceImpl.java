package com.px.tool.domain.request.service.impl;

import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.DashBoardPayload;
import com.px.tool.domain.request.repository.RequestRepository;
import com.px.tool.domain.request.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Override
    @Transactional
    public Request save(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public DashBoardPayload timByNguoiGui(Collection<Long> userIds) {
        List<Request> requestsByNguoiGui = requestRepository.findByNguoiGui(userIds);
        DashBoardPayload dashBoardPayload = new DashBoardPayload();
        requestsByNguoiGui
                .stream()
                .forEach(dashBoardPayload::fromEntity);
        return dashBoardPayload;
    }

    @Override
    public DashBoardPayload timByNguoiNhan(Collection<Long> userIds) {
        List<Request> requestsByNguoiGui = requestRepository.findByNguoiGui(userIds);

        DashBoardPayload dashBoardPayload = new DashBoardPayload();
        requestsByNguoiGui
                .stream()
                .forEach(dashBoardPayload::fromEntity);
        return dashBoardPayload;
    }

    @Override
    public Request findById(Long id) {
        return this.requestRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
    }
}
