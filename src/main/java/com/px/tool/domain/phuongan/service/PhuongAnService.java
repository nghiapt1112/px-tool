package com.px.tool.domain.phuongan.service;

import com.px.tool.domain.phuongan.PhuongAn;
import com.px.tool.domain.phuongan.PhuongAnPayload;

import java.util.List;

public interface PhuongAnService {
    PhuongAnPayload findById(Long userId, Long id);

    List<PhuongAn> findByPhongBan(Long userId);

    PhuongAn save(Long userId, PhuongAnPayload phuongAnPayload);

    PhuongAn savePhuongAn(PhuongAn phuongAn);
}
