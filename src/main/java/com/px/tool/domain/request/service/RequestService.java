package com.px.tool.domain.request.service;

import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.DashBoardPayload;

import java.util.Collection;

public interface RequestService {
    Request save(Request request);

    Request findById(Long id);

    DashBoardPayload timByNguoiGui(Collection<Long> userIds);

    DashBoardPayload timByNguoiNhan(Collection<Long> userIds);
}
