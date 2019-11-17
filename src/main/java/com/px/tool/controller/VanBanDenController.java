package com.px.tool.controller;

import com.px.tool.domain.vanbanden.VanBanDenPayload;
import com.px.tool.domain.vanbanden.service.VanBanDenServiceImpl;
import com.px.tool.infrastructure.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vbd")
public class VanBanDenController extends BaseController {
    @Autowired
    private VanBanDenServiceImpl vanBanDenService;

    @GetMapping
    public List<VanBanDenPayload> list() {
        return vanBanDenService.findAll();
    }


    @PostMapping
    public VanBanDenPayload save(@RequestBody VanBanDenPayload payload) {
        return vanBanDenService.save(payload);
    }

}
