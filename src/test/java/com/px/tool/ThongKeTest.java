package com.px.tool;

import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.payload.ThongKePayload;
import com.px.tool.domain.request.payload.ThongKeRequest;
import com.px.tool.domain.request.repository.RequestRepository;
import com.px.tool.domain.request.service.impl.RequestServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class ThongKeTest extends PxApplicationTests{
    @Autowired
    private RequestRepository requestRepository;


    @Autowired
    private RequestServiceImpl requestService;

    @Test
    public void hi() {
        ThongKeRequest request = new ThongKeRequest();
        request.setPage(1);
        request.setSize(10);
        Pageable pageRequest = PageRequest.of(1,10);
//        List<Request> result = requestRepository.findAll();
        Page<Request> result = requestRepository.findPaging(request, pageRequest);
        System.out.println();
    }

    @Test
    public void thongKe() {
        ThongKePayload data = requestService.collectDataThongKe(1l);
        System.out.println();
    }
}
