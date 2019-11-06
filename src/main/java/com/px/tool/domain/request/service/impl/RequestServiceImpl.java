package com.px.tool.domain.request.service.impl;

import com.px.tool.domain.request.DashBoardCongViecCuaToi;
import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.ThongKePayload;
import com.px.tool.domain.request.repository.RequestRepository;
import com.px.tool.domain.request.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RequestServiceImpl implements RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Override
    @Transactional
    public Request save(Request request) {
        return requestRepository.save(request);
    }

    @Override
    public List<DashBoardCongViecCuaToi> timByNguoiGui(Collection<Long> userIds) {
        List<Request> requestsByNguoiGui = requestRepository.findByNguoiGui(userIds);
        return requestsByNguoiGui
                .stream()
                .map(DashBoardCongViecCuaToi::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<DashBoardCongViecCuaToi> timByNguoiNhan(Collection<Long> userIds) {
        List<Request> requestsByNguoiGui = requestRepository.findByNguoiGui(userIds);
        return requestsByNguoiGui
                .stream()
                .map(DashBoardCongViecCuaToi::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Request findById(Long id) {
        return this.requestRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
    }

    @Override
    public List<ThongKePayload> collectDataThongKe(Long userId) {
        List<Request> requests = requestRepository.findAll();
        List<ThongKePayload> tks = requests.stream()
                .map(ThongKePayload::fromRequestEntity)
                .collect(Collectors.toList());
        Request request = findById(userId);
        return IntStream.rangeClosed(1, 20)
                .mapToObj(el -> {
                    ThongKePayload tk = new ThongKePayload();
                    tk.tt = Long.valueOf(el);
                    tk.tenPhuKien = "Ten phi kien __ " + el;
                    tk.tenLinhKien = "Ten link kien " + el;
                    tk.kyHieu = "Ki hieu " + el;
                    tk.SL = 10L;
                    tk.dangHuHong = "Dang hu hong" + el;
                    tk.ngayKiemHong = "ngay kiem hong" + el;
                    tk.phuongPhapKhacPhuc = "phuong phap khac phuc" + el;
                    tk.ngayChuyenPhongVatTu = "11/12/2018";
                    tk.soPhieuDatHang = "so phieu dat hang" + el;
                    tk.ngayChuyenKT = "15/03/2018";
                    tk.soPA = "So phuong an" + el;
                    tk.ngayRaPA = "15/03/2018";
                    tk.ngayChuyenKH = "15/03/2018";
                    tk.ngayPheDuyet = "15/03/2018";
                    tk.ngayHoanThanh = "15/03/2018";
                    tk.xacNhanHoanThanh = "Da hoan thanh";
                    return tk;
                })
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
