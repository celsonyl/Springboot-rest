package com.celso.springrest.services;

import com.celso.springrest.config.FileStorageConfig;
import com.celso.springrest.domain.UploadFileDomain;
import com.celso.springrest.exceptions.handler.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class FileStorageService {

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

    private void populateUploadFileDomain(MultipartFile multipartFile, String filename) {
        uploadFileDomain.setFileName(filename);
        uploadFileDomain.setSize(multipartFile.getSize());
        uploadFileDomain.setFileType(multipartFile.getContentType());
    }
}