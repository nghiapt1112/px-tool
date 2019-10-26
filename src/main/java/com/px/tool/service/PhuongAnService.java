package com.px.tool.service;

import com.px.tool.model.PhuongAn;

import java.util.List;

public interface PhuongAnService {
    PhuongAn findById(Long id);

    List<PhuongAn> findByPhongBan(Long userId);

    PhuongAn createPhuongAn(PhuongAn phuongAn);

    PhuongAn savePhuongAn(PhuongAn phuongAn);
}
