package com.px.tool.domain.request.repository;

import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.payload.ThongKeRequest;

import java.util.List;

public interface RequestRepositoryCustom {
    List<Request> findPaging(ThongKeRequest thongKeRequest);
}
