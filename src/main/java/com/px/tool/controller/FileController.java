package com.px.tool.controller;

import com.px.tool.domain.RequestType;
import com.px.tool.domain.file.FileStorage;
import com.px.tool.domain.file.FileStorageService;
import com.px.tool.domain.file.repository.FileStorageRepository;
import com.px.tool.infrastructure.BaseController;
import com.px.tool.infrastructure.service.ExcelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private FileStorageRepository fileStorageRepository;


    @PostMapping("/upload")
    public void uploadMultipleFiles(@RequestParam RequestType requestType, @RequestParam MultipartFile[] files, @RequestParam Long requestId) {
        logger.info("Number of request files: {}", files.length);
        // if requestId existed. => override requestId
        fileStorageRepository.deleteAll(Arrays.asList(requestId));
        for (MultipartFile file : files) {
            fileStorageService.storeFile(file, requestType, requestId);
        }
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        return super.toFile(request, fileStorageService.loadFileAsResource(fileName));
    }

    @GetMapping
    public List<FileStorage> listFile(@RequestParam RequestType requestType, @RequestParam Long requestId) {
        return fileStorageService.listFile(requestType, requestId);
    }

    @DeleteMapping("/del/{id}")
    public void deleteFile(@PathVariable Long id) {
        fileStorageService.delete(id);
    }

    @GetMapping("/print")
    public void downloadKiemHong(@RequestParam Long requestId, HttpServletRequest request, HttpServletResponse response, RequestType requestType) throws IOException {
        excelService.exportFile(requestId, requestType, response.getOutputStream());
        response.setHeader("Content-disposition", "attachment; filename=export.xlsx");
        response.setHeader("Content-type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    @GetMapping("/export")
    public String exportThongKe(@RequestParam Long startDate, @RequestParam Long endDate) {
        excelService.exports(startDate, endDate);
        return "export done";
    }

}