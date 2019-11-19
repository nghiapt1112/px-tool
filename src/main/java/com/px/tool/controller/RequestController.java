package com.px.tool.controller;

import com.px.tool.domain.request.NoiNhan;
import com.px.tool.domain.request.NotificationPayload;
import com.px.tool.domain.request.PhanXuongPayload;
import com.px.tool.domain.request.ToSXPayload;
import com.px.tool.domain.user.NoiNhanRequestParams;
import com.px.tool.domain.user.service.UserService;
import com.px.tool.infrastructure.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/req")
public class RequestController extends BaseController {
    @Autowired
    private UserService userService;

    @GetMapping("/noi-nhan")
    public List<NoiNhan> getListNoiNhan(HttpServletRequest httpServletRequest,
                                        @RequestParam(required = false) Long requestId,
                                        @RequestParam(required = false) Boolean toTruong,
                                        @RequestParam(required = false) Boolean troLyKT,
                                        @RequestParam(required = false) Boolean quanDoc,
                                        //
                                        @RequestParam(required = false) Boolean nguoiDatHang,
                                        //
                                        @RequestParam(required = false) Boolean nguoiLap,
                                        @RequestParam(required = false) Boolean tpVatTu,
                                        @RequestParam(required = false) Boolean tpKeHoach,
                                        @RequestParam(required = false) Boolean tpKTHK,
                                        //
                                        @RequestParam(required = false) Boolean tpKCS,
                                        @RequestParam(required = false) Boolean nguoiThucHien,
                                        @RequestParam(required = false) Boolean nguoiGiaoViec,

                                        @RequestParam(required = false) Long userId

    ) {

        return userService.findNoiNhan(extractUserInfo(httpServletRequest), NoiNhanRequestParams.builder()
                .requestId(requestId)
                .toTruong(toTruong)
                .troLyKT(troLyKT)
                .quanDoc(quanDoc)
                .nguoiDatHang(nguoiDatHang)
                .nguoiLap(nguoiLap)
                .tpVatTu(tpVatTu)
                .tpKeHoach(tpKeHoach)
                .tpKTHK(tpKTHK)
                .tpKCS(tpKCS)
                .nguoiThucHien(nguoiThucHien)
                .nguoiGiaoViec(nguoiGiaoViec)
                .build());
    }

    @GetMapping("/vbd/noi-nhan")
    public List<NoiNhan> getListNoiNhan() {
        return userService.findVanBanDenNoiNhan();
    }

    @GetMapping("/phan-xuong")
    public List<PhanXuongPayload> getPhanXuong() {
        return userService.findListPhanXuong();
    }

    @GetMapping("/to-sx")
    public List<ToSXPayload> getToSanXuat(@RequestParam Long pxId) {
        return userService.findListToSanXuat(pxId);
    }

    @GetMapping
    public List<NotificationPayload> getNotification() {
        return IntStream.rangeClosed(1, 3)
                .mapToObj(el -> {
                    NotificationPayload payload = new NotificationPayload();
                    payload.setRequestId(Long.valueOf(el));
                    payload.setNotiId(Long.valueOf(el));
                    payload.setBody("Bạn đang có 1 thông báo mới. click ");
                    if (el % 2 == 0) {
                        payload.setRead(true);
                    } else {
                        payload.setRead(false);
                    }
                    return payload;
                })
                .collect(Collectors.toList());
    }
}
