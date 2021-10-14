package com.celso.springrest.translator;

import com.celso.springrest.controller.model.PersonRequest;
import com.celso.springrest.controller.model.PersonResponse;
import com.celso.springrest.gateway.model.PersonDatabase;
import org.mapstruct.Mapper;

@Mapper
public interface PersonMapper {

    PersonRequest personDatabaseToRequest(PersonDatabase personDatabase);

    PersonResponse personDatabaseToResponse(PersonDatabase personDatabase);

    PersonDatabase personRequestToDatabase(PersonRequest personRequest);

    PersonDatabase personResponseToDatabase(PersonResponse personResponse);
}
