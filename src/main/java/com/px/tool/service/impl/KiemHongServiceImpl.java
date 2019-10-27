package com.px.tool.service.impl;

import com.px.tool.model.KiemHong;
import com.px.tool.model.KiemHongDetail;
import com.px.tool.model.response.KiemHongPayLoad;
import com.px.tool.repository.KiemHongRepository;
import com.px.tool.service.KiemHongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class KiemHongServiceImpl implements KiemHongService {
    @Autowired
    private KiemHongRepository kiemHongRepository;

    @Override
    public List<KiemHongPayLoad> findThongTinKiemHongCuaPhongBan(Long userId) {
        // TODO: tu thong tin user, get tat ca kiemHong dang can xu ly 1 phong ban.
        //  => viec luu createdId nen de la phongBan id.
        return kiemHongRepository.findByCreatedBy(userId)
                .stream()
                .filter(Objects::nonNull)
                .map(KiemHongPayLoad::fromEntity)
                .collect(Collectors.toList());

    }

    @Override
    public KiemHongPayLoad findThongTinKiemHong(Long id) {
        KiemHong kiemHong = kiemHongRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Khong tin thay Kiem Hong, Id = " + id));
        if (kiemHong == null) {
            kiemHong = new KiemHong();
            Set<KiemHongDetail> kiemHongDetails = new HashSet<>();
            kiemHong.setKiemHongDetails(kiemHongDetails);
        }
        return KiemHongPayLoad.fromEntity(kiemHong);
    }

    @Override
    public KiemHong taoYeuCauKiemHong(KiemHong kiemHong) {
        return kiemHongRepository.save(kiemHong);
    }
}
