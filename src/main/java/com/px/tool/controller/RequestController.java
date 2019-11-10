package com.px.tool.controller;

import com.px.tool.domain.request.NoiNhan;
import com.px.tool.domain.user.service.UserService;
import com.px.tool.infrastructure.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

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
        return userService.findNoiNhan(extractUserInfo(httpServletRequest), requestId);
    }

}
