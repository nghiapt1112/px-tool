package com.px.tool.domain.request.service;

import com.px.tool.domain.request.DashBoardCongViecCuaToi;
import com.px.tool.domain.request.Request;

import java.util.Collection;
import java.util.List;

public interface RequestService {
    Request save(Request request);

    Request findById(Long id);

    List<DashBoardCongViecCuaToi> timByNguoiGui(Collection<Long> userIds);

    List<DashBoardCongViecCuaToi> timByNguoiNhan(Collection<Long> userIds);
}
