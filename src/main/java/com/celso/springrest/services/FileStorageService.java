package com.celso.springrest.services;

import com.celso.springrest.config.FileStorageConfig;
import com.celso.springrest.controller.FileController;
import com.celso.springrest.domain.UploadFileDomain;
import com.celso.springrest.exceptions.handler.FileNotFoundException;
import com.celso.springrest.exceptions.handler.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileStorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final Path fileStorageLocation;
    private final static UploadFileDomain uploadFileDomain = new UploadFileDomain();

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        this.fileStorageLocation = Paths.get(fileStorageConfig.getUploadDir())
                .toAbsolutePath()
                .normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create directory where uploaded files will be storage", ex);
        }
    }

    public UploadFileDomain storageFile(MultipartFile multipartFile) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        try {
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! filename contains invalid path sequence " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            populateUploadFileDomain(multipartFile, fileName);
            return uploadFileDomain;
        } catch (Exception ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found!" + fileName);
            }

        } catch (Exception ex) {
            throw new FileNotFoundException("File not found!" + fileName);
        }
    }

    public String verifyContentType(String filename, String content, HttpServletRequest httpServletRequest) {
        Resource resource = this.loadFileAsResource(filename);

        try {
            content = httpServletRequest.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception ex) {
            logger.info("Could not determine file type");
        }

        if (content == null) {
            content = "application/octet-stream";
        }
        return content;
    }

    private void populateUploadFileDomain(MultipartFile multipartFile, String filename) {
        uploadFileDomain.setFileName(filename);
        uploadFileDomain.setSize(multipartFile.getSize());
        uploadFileDomain.setFileType(multipartFile.getContentType());
    }
}