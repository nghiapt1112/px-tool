package com.px.tool.infrastructure.service;

import com.px.tool.domain.RequestType;

import javax.servlet.http.HttpServletResponse;

public interface ExcelService {
    void exportFile(Long requestId, RequestType requestType, HttpServletResponse response);
}
