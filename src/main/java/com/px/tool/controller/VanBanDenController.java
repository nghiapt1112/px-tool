package com.px.tool.controller;

import com.px.tool.domain.vanbanden.payload.VanBanDenDetail;
import com.px.tool.domain.vanbanden.payload.VanBanDenPageRequest;
import com.px.tool.domain.vanbanden.payload.VanBanDenPageResponse;
import com.px.tool.domain.vanbanden.payload.VanBanDenRequest;
import com.px.tool.domain.vanbanden.payload.VanBanDenResponse;
import com.px.tool.domain.vanbanden.service.VanBanDenServiceImpl;
import com.px.tool.infrastructure.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/vbd")
public class VanBanDenController extends BaseController {
    @Autowired
    private VanBanDenServiceImpl vanBanDenService;

    @GetMapping
    public VanBanDenPageResponse findSent(HttpServletRequest httpServletRequest,
                                          @RequestParam(required = false, defaultValue = "1") Integer page,
                                          @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        return vanBanDenService.findAll(extractUserInfo(httpServletRequest), new VanBanDenPageRequest(page, size));

    }

    @GetMapping("/receive")
    public VanBanDenPageResponse findInBox(HttpServletRequest httpServletRequest,
                                           @RequestParam(required = false, defaultValue = "1") Integer page,
                                           @RequestParam(required = false, defaultValue = "10") Integer size) {
        return vanBanDenService.findInBox(extractUserInfo(httpServletRequest), new VanBanDenPageRequest(page, size));
    }

    @PostMapping
    public VanBanDenResponse save(HttpServletRequest httpServletRequest, @RequestBody VanBanDenRequest payload) {
        return vanBanDenService.save(extractUserInfo(httpServletRequest), payload);
    }

    @GetMapping("/{id}")
    public VanBanDenDetail detail(@PathVariable Long id) {
        return vanBanDenService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        vanBanDenService.deleteById(id);
    }

}
