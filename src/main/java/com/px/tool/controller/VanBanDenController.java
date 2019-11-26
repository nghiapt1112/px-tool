package com.px.tool.controller;

import com.px.tool.domain.vanbanden.VanBanDenRequest;
import com.px.tool.domain.vanbanden.VanBanDenResponse;
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
import java.util.List;

@RestController
@RequestMapping("/vbd")
public class VanBanDenController extends BaseController {
    @Autowired
    private VanBanDenServiceImpl vanBanDenService;

    @GetMapping
    public List<VanBanDenResponse> findSent(HttpServletRequest httpServletRequest,
                                            @RequestParam(required = false) Long page,
                                            @RequestParam(required = false) Long size
    ) {
        return vanBanDenService.findAll(extractUserInfo(httpServletRequest));
    }

    @GetMapping("/receive")
    public List<VanBanDenResponse> findInBox(HttpServletRequest httpServletRequest) {
        return vanBanDenService.findInBox(extractUserInfo(httpServletRequest));
    }

    @PostMapping
    public VanBanDenResponse save(HttpServletRequest httpServletRequest, @RequestBody VanBanDenRequest payload) {
        return vanBanDenService.save(extractUserInfo(httpServletRequest), payload);
    }

    @GetMapping("/{id}")
    public VanBanDenResponse detail(@PathVariable Long id) {
        return vanBanDenService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        vanBanDenService.deleteById(id);
    }

}
