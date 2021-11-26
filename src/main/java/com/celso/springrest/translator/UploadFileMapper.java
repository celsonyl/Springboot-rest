package com.celso.springrest.translator;

import com.celso.springrest.controller.model.UploadFileResponse;
import com.celso.springrest.domain.UploadFileDomain;
import org.mapstruct.Mapper;

@Mapper
public interface UploadFileMapper {

    UploadFileResponse uploadFileDomainToResponse(UploadFileDomain uploadFileDomain);
}