package com.px.tool.controller;

import com.px.tool.domain.RequestType;
import com.px.tool.domain.file.FileStorage;
import com.px.tool.domain.file.FileStorageService;
import com.px.tool.infrastructure.BaseController;
import com.px.tool.infrastructure.service.ExcelService;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ExcelService excelService;


    @PostMapping("/upload")
    public void uploadMultipleFiles(@RequestParam RequestType requestType, @RequestParam MultipartFile[] files, @RequestParam Long requestId) {
        logger.info("Number of request files: {}", files.length);
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
//        excelService.exportFile(requestId, requestType, response);
        File file = new File("/mnt/project/Sources/NGHIA/free/px-toool/src/main/resources/templates/new_Kiem_Hong.xlsx");
//        new ByteArrayResource(Files.readAllBytes(path));


        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=file.xlsx");
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

//        return ResponseEntity.ok()
//                .headers(header)
//                .contentLength(file.length())
//                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
//                .body(resource);
        response.setHeader("Content-disposition", "attachment; filename=test2.xlsx");
        response.setHeader("Content-type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        response.setHeader("Content-type", "vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.getOutputStream().write(Files.readAllBytes(path));
    }

}