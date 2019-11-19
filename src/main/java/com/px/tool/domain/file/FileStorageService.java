package com.px.tool.domain.file;

import com.px.tool.domain.RequestType;
import com.px.tool.domain.file.repository.FileStorageRepository;
import com.px.tool.infrastructure.exception.PXException;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileStorageService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Path fileStorageLocation;

    @Autowired
    private FileStorageRepository fileStorageRepository;

    @PostConstruct
    public void init() {
        this.fileStorageLocation = Paths.get("./nghia-file/").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Transactional
    public String storeFile(MultipartFile file, RequestType requestType, Long requestId) {
        String orgFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileName = requestType.name()
                .concat(UUID.randomUUID().toString())
                .concat(".")
                .concat(FilenameUtils.getExtension(orgFileName));

        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
        FileStorage fileInfo = new FileStorage();
        fileInfo.setFileName(orgFileName);
        fileInfo.setFileUUId(fileName);
        fileInfo.setRequestType(requestType);
        fileInfo.setRequestId(requestId);
        Optional<FileStorage> existedFile = fileStorageRepository.findByFileName(orgFileName);
        if (!existedFile.isPresent()) {
            this.fileStorageRepository.save(fileInfo);
        }
        return fileName;
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            FileStorage fileInfo = fileStorageRepository.findByFileName(fileName)
                    .orElseThrow(() -> new PXException("File not found"));

            Path filePath = this.fileStorageLocation.resolve(fileInfo.getFileUUId()).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new MyFileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
    }

    public List<FileStorage> listFile(RequestType requestType, Long requestId) {
        return fileStorageRepository.findFiles(requestId, requestType)
                .orElse(Collections.emptyList());
    }

    public void delete(Long id) {
        fileStorageRepository.deleteById(id);
    }

    public List<String> listFileNames(RequestType requestType, Long id) {
        List<FileStorage> files = listFile(requestType, id);
        List<String> fileNames = new ArrayList<>(files.size());
        for (FileStorage fileStorage : files) {
            fileNames.add(fileStorage.getFileName());
        }
        logger.info("\nTotal files in {}: {}\n", requestType, fileNames.size());
        return fileNames;
    }
}