package com.px.tool.domain.request.repository;

import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.payload.ThongKePageRequest;

import java.util.List;

public interface RequestRepositoryCustom {
    List<Request> findPaging(ThongKePageRequest thongKeRequest);

    Long findPhuongAnId(Long requsetId);

}
