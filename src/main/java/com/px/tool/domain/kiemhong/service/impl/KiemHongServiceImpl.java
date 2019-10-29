package com.px.tool.domain.kiemhong.service.impl;

import com.px.tool.domain.cntp.CongNhanThanhPham;
import com.px.tool.domain.kiemhong.KiemHong;
import com.px.tool.domain.kiemhong.KiemHongDetail;
import com.px.tool.domain.dathang.PhieuDatHang;
import com.px.tool.domain.phuongan.PhuongAn;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.RequestType;
import com.px.tool.domain.kiemhong.KiemHongPayLoad;
import com.px.tool.domain.kiemhong.repository.KiemHongRepository;
import com.px.tool.domain.kiemhong.service.KiemHongService;
import com.px.tool.domain.request.service.RequestService;
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

    @Autowired
    private RequestService requestService;

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
        Request request = requestService.findById(id);
        if (request != null) {
            return KiemHongPayLoad.fromEntity(request.getKiemHong());
        }
        throw new RuntimeException("Kiem hong not found");
    }

    @Override
    public KiemHongPayLoad taoYeuCauKiemHong(Long userId, KiemHong kiemHong) {
        // TODO: khi tao kiem hong thi cung phai co status: approve hay chua, khi nao approve
        Request request = new Request();
        request.setCreatedBy(userId);
        request.setKiemHong(kiemHong);
        request.setCongNhanThanhPham(new CongNhanThanhPham());
        request.setPhuongAn(new PhuongAn());
        request.setPhieuDatHang(new PhieuDatHang());
        Request savedRequest = this.requestService.save(request);
        // TODO: validate kiem hong de co the biet dc khi nao thji chuyen  status sang job khac
        request.setStatus(RequestType.DAT_HANG);
        return KiemHongPayLoad
                .fromEntity(savedRequest.getKiemHong())
                .andRequestId(savedRequest.getRequestId());
    }

    @Override
    public KiemHongPayLoad capNhatKiemHong(Long userId, Long requestId, KiemHong kiemHong) {
        Request request = requestService.findById(requestId);
        request.setKiemHong(kiemHong);
        Request savedRequest = requestService.save(request);

        return KiemHongPayLoad
                .fromEntity(savedRequest.getKiemHong())
                .andRequestId(savedRequest.getRequestId());
    }
}
