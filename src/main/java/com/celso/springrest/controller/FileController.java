package com.celso.springrest.controller;

import com.celso.springrest.controller.model.UploadFileResponse;
import com.celso.springrest.services.FileStorageService;
import com.celso.springrest.translator.UploadFileMapperImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Api(tags = "FileEndpoint")
@RestController
@RequestMapping(value = "/file")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/uploadFile")
    public ResponseEntity<UploadFileResponse> uploadFile(@RequestParam("file") MultipartFile multipartFile) {
        var fileMapper = new UploadFileMapperImpl();

        var fileDomain = fileStorageService.storageFile(multipartFile);
        var uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/file/downloadFile/").path(fileDomain.getFileName()).toUriString();
        fileDomain.setFileDownloadUri(uri);

        return ResponseEntity.created(URI.create(uri)).body(fileMapper.uploadFileDomainToResponse(fileDomain));
    }
}