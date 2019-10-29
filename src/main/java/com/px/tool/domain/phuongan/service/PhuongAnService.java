package com.px.tool.domain.phuongan.service;

import com.px.tool.domain.phuongan.PhuongAn;
import com.px.tool.domain.phuongan.PhuongAnPayload;

import java.util.List;

public interface PhuongAnService {
    PhuongAnPayload findById(Long id);

    List<PhuongAn> findByPhongBan(Long userId);

    PhuongAn createPhuongAn(PhuongAn phuongAn);

    PhuongAn savePhuongAn(PhuongAn phuongAn);
}
