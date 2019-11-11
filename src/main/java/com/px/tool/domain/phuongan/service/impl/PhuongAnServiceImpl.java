package com.px.tool.domain.phuongan.service.impl;

import com.px.tool.domain.RequestType;
import com.px.tool.domain.cntp.CongNhanThanhPham;
import com.px.tool.domain.cntp.repository.CongNhanThanhPhamRepository;
import com.px.tool.domain.phuongan.DinhMucLaoDong;
import com.px.tool.domain.phuongan.DinhMucVatTu;
import com.px.tool.domain.phuongan.PhuongAn;
import com.px.tool.domain.phuongan.PhuongAnPayload;
import com.px.tool.domain.phuongan.repository.DinhMucLaoDongRepository;
import com.px.tool.domain.phuongan.repository.DinhMucVatTuRepository;
import com.px.tool.domain.phuongan.repository.PhuongAnRepository;
import com.px.tool.domain.phuongan.service.PhuongAnService;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.service.RequestService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PhuongAnServiceImpl implements PhuongAnService {
    @Autowired
    private PhuongAnRepository phuongAnRepository;

    @Autowired
    private RequestService requestService;

    @Autowired
    private DinhMucVatTuRepository dinhMucVatTuRepository;

    @Autowired
    private DinhMucLaoDongRepository dinhMucLaoDongRepository;

    @Autowired
    private CongNhanThanhPhamRepository congNhanThanhPhamRepository;

    @Override
    public PhuongAnPayload findById(Long id) {
        Request request = requestService.findById(id);
        PhuongAnPayload payload = PhuongAnPayload.fromEntity(request.getPhuongAn());
        payload.setRequestId(request.getRequestId());
        return payload;
    }

    @Override
    public List<PhuongAn> findByPhongBan(Long userId) {
        return null;
    }

    @Override
    @Transactional
    public PhuongAn save(PhuongAnPayload phuongAnPayload) {
        if (Objects.isNull(phuongAnPayload.getPaId())) {
            throw new RuntimeException("Phuong an phai co id");
        }
        PhuongAn existedPhuongAn = phuongAnRepository
                .findById(phuongAnPayload.getPaId())
                .orElse(null);

        cleanOldDetailData(existedPhuongAn);

        CongNhanThanhPham thanhPham = existedPhuongAn.getRequest().getCongNhanThanhPham();
        PhuongAn phuongAn = new PhuongAn();
        phuongAnPayload.toEntity(phuongAn);
        if (phuongAn.allApproved()) {
            existedPhuongAn.getRequest().setStatus(RequestType.CONG_NHAN_THANH_PHAM);
            phuongAn.setRequest(existedPhuongAn.getRequest());
            taoCNTP(phuongAn, thanhPham);
        }
        return phuongAnRepository.save(phuongAn);
    }

    private void taoCNTP(PhuongAn phuongAn, CongNhanThanhPham congNhanThanhPham) {
//        CongNhanThanhPham congNhanThanhPham = new CongNhanThanhPham();
        congNhanThanhPham.setTenSanPham(phuongAn.getSanPham());
        congNhanThanhPham.setNoiDung(phuongAn.getNoiDung());
        congNhanThanhPham.setSoPA(phuongAn.getMaSo());
        congNhanThanhPham.setDonviThucHien("neu chuyen ve px 3 hien px 3"); // TODO : set ten phan xuong vao day

        congNhanThanhPhamRepository.save(congNhanThanhPham);
    }

    private void cleanOldDetailData(PhuongAn existedPhuongAn) {
        try {
            if (Objects.isNull(existedPhuongAn)) {
                return;
            }
            Set<Long> vatuIds = existedPhuongAn.getDinhMucVatTus()
                    .stream()
                    .map(DinhMucVatTu::getVtId)
                    .collect(Collectors.toSet());
            Set<Long> laoDongIds = existedPhuongAn.getDinhMucLaoDongs()
                    .stream()
                    .map(DinhMucLaoDong::getDmId)
                    .collect(Collectors.toSet());
            if (!CollectionUtils.isEmpty(vatuIds)) {
                dinhMucVatTuRepository.deleteAllByIds(vatuIds);
            }
            if (!CollectionUtils.isEmpty(laoDongIds)) {
                dinhMucLaoDongRepository.deleteAllByIds(laoDongIds);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Co loi xay ra trong qua trinh clean DinhMucVatTu, DinhMucLaoDong");
        }
    }

    @Override
    public PhuongAn savePhuongAn(PhuongAn phuongAn) {
        return this.phuongAnRepository.save(phuongAn);
    }
}
