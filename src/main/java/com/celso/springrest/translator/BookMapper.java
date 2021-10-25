package com.celso.springrest.translator;

import com.celso.springrest.controller.model.BookRequest;
import com.celso.springrest.controller.model.BookResponse;
import com.celso.springrest.domain.BookDomain;
import com.celso.springrest.gateway.model.BookDatabase;
import org.mapstruct.Mapper;

@Mapper
public interface BookMapper {

    BookDomain bookDatabaseToDomain(BookDatabase bookDatabase);

    BookDatabase bookDomainToDatabase(BookDomain bookDomain);

    BookDomain bookRequestToDomain(BookRequest bookRequest);

    BookResponse bookDomainToResponse(BookDomain bookDomain);
}