package com.px.tool.domain.request.service;

import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.payload.PageDashBoardCongViecCuaToi;
import com.px.tool.domain.request.payload.ThongKePageRequest;
import com.px.tool.domain.request.payload.ThongKePageResponse;
import com.px.tool.infrastructure.model.payload.PageRequest;

public interface RequestService {
    Request save(Request request);

    Request findById(Long id);

    PageDashBoardCongViecCuaToi timByNguoiNhan(Long userId, PageRequest pageRequest);

    ThongKePageResponse collectDataThongKe(ThongKePageRequest request);

    void updateReceiveId(Long requestId, Long kiemHongReceiverId, Long phieuDatHangReceiverId, Long phuongAnReceiverId, Long cntpReceiverId);

}
