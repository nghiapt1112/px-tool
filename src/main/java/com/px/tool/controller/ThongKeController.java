package com.px.tool.controller;

import com.px.tool.domain.request.ThongKePayload;
import com.px.tool.domain.request.service.RequestService;
import com.px.tool.infrastructure.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/thongke")
public class ThongKeController extends BaseController {

    @Autowired
    private RequestService requestService;

    @GetMapping
    private List<ThongKePayload> getDataChoThongKe(HttpServletRequest httpServletRequest) {
        Long userId = extractUserInfo(httpServletRequest);
//        return requestService.collectDataThongKe(userId, 1L);
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
