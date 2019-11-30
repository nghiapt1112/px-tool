package com.px.tool.domain.request.service;

import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.payload.PageDashBoardCongViecCuaToi;
import com.px.tool.domain.request.payload.PageRequest;
import com.px.tool.domain.request.payload.PageThongKePayload;
import com.px.tool.domain.request.payload.ThongKeRequest;

public interface RequestService {
    Request save(Request request);

    Request findById(Long id);

    PageDashBoardCongViecCuaToi timByNguoiNhan(Long userId, PageRequest pageRequest);

    PageThongKePayload collectDataThongKe(ThongKeRequest request);

    void updateReceiveId(Long requestId, Long kiemHongReceiverId, Long phieuDatHangReceiverId, Long phuongAnReceiverId, Long cntpReceiverId);

}
