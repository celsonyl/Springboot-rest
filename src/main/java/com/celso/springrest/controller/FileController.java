package com.celso.springrest.controller;

import com.celso.springrest.controller.model.UploadFileResponse;
import com.celso.springrest.services.FileStorageService;
import com.celso.springrest.translator.UploadFileMapperImpl;
import com.google.common.net.HttpHeaders;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/uploadFiles")
    public ResponseEntity<List<UploadFileResponse>> uploadFiles(@RequestParam("files") MultipartFile[] multipartFiles) {
        var uploadFile = Arrays.stream(multipartFiles).map(this::uploadFile).collect(Collectors.toList());

        return ResponseEntity.ok().body(uploadFile.stream()
                .map(HttpEntity::getBody)
                .collect(Collectors.toList()));
    }

    @GetMapping(value = "/downloadFile/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("filename") String filename, HttpServletRequest httpServletRequest) {
        Resource resource = fileStorageService.loadFileAsResource(filename);
        String contextType;

        contextType = fileStorageService.verifyContentType(filename, null, httpServletRequest);

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contextType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}