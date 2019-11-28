package com.px.tool.infrastructure.service;

import com.px.tool.domain.RequestType;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;

public interface ExcelService {
    void exportFile() throws IOException;

    File exportFile(Long requestId, RequestType requestType);
}
