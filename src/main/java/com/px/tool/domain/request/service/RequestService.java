package com.px.tool.domain.request.service;

import com.px.tool.domain.request.DashBoardCongViecCuaToi;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.ThongKeDetailPayload;
import com.px.tool.domain.request.ThongKePayload;
import com.px.tool.domain.request.payload.ThongKeRequest;

import java.util.Collection;
import java.util.List;

public interface RequestService {
    Request save(Request request);

    Request findById(Long id);

    List<DashBoardCongViecCuaToi> timByNguoiNhan(Long userId);

    ThongKePayload collectDataThongKe(ThongKeRequest request);

    void updateReceiveId(Long requestId, Long kiemHongReceiverId, Long phieuDatHangReceiverId, Long phuongAnReceiverId, Long cntpReceiverId);
}
