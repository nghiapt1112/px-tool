package com.px.tool.domain.request.service;

import com.px.tool.domain.request.payload.DashBoardCongViecCuaToi;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.payload.PageRequest;
import com.px.tool.domain.request.payload.ThongKePayload;
import com.px.tool.domain.request.payload.ThongKeRequest;

import java.util.List;

public interface RequestService {
    Request save(Request request);

    Request findById(Long id);

    List<DashBoardCongViecCuaToi> timByNguoiNhan(Long userId, PageRequest pageRequest);

    ThongKePayload collectDataThongKe(ThongKeRequest request);

    void updateReceiveId(Long requestId, Long kiemHongReceiverId, Long phieuDatHangReceiverId, Long phuongAnReceiverId, Long cntpReceiverId);
}
