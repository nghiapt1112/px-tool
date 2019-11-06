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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/req")
public class RequestController extends BaseController {
    @Autowired
    private UserService userService;

    @GetMapping("/noi-nhan")
    public LinkedList<NoiNhan> getListNoiNhan(HttpServletRequest httpServletRequest, @RequestParam String requestId) {
        return IntStream.rangeClosed(1,10)
                .mapToObj(el -> {
                    NoiNhan noiNhan = new NoiNhan();
                    noiNhan.setId(Long.valueOf(el));
                    noiNhan.setName("Name __" + el);
                    return noiNhan;
                })
                .collect(Collectors.toCollection(LinkedList::new));
    }

}
