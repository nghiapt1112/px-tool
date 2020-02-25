package com.px.tool.infrastructure.service;

import com.px.tool.domain.RequestType;

import java.io.OutputStream;

public interface ExcelService {
    void exportFile(Long requestId, RequestType requestType, OutputStream outputStream);

    void exports(Long startDate, Long endDate);
}
