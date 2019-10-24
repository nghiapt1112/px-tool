package com.px.tool.controller;

import com.px.tool.infrastructure.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/req")
public class RequestController extends BaseController {
    @GetMapping
    public void detailRequest() {

    }

    @PostMapping
    public void createRequest() {

    }
}
