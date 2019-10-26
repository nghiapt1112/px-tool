package com.px.tool.service.impl;

import com.px.tool.model.PhongBan;
import com.px.tool.model.PhuongAn;
import com.px.tool.repository.PhuongAnRepository;
import com.px.tool.service.PhuongAnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhuongAnServiceImpl implements PhuongAnService {
    @Autowired
    private PhuongAnRepository phuongAnRepository;

    @Override
    public PhuongAn findById(Long id) {
        return phuongAnRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Phuong An not found"));
    }

    @Override
    public List<PhuongAn> findByPhongBan(Long userId) {
        return null;
    }

    @Override
    public PhongBan createPhuongAn(PhuongAn phuongAn) {
        return null;
    }

    @Override
    public PhongBan savePhuongAn(PhuongAn phuongAn) {
        return null;
    }
}
