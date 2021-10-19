package com.celso.springrest.translator;

import com.celso.springrest.controller.model.PersonRequest;
import com.celso.springrest.controller.model.PersonRequestV2;
import com.celso.springrest.controller.model.PersonResponse;
import com.celso.springrest.controller.model.PersonResponseV2;
import com.celso.springrest.gateway.model.PersonDatabase;
import org.mapstruct.Mapper;

@Mapper
public interface PersonMapper {

    PersonResponse personDatabaseToResponse(PersonDatabase personDatabase);

    PersonDatabase personRequestToDatabase(PersonRequest personRequest);

    PersonDatabase personResponseToDatabase(PersonResponse personResponse);

    PersonDatabase personRequestV2ToDatabase(PersonRequestV2 personRequestV2);

    PersonResponseV2 personDatabaseToResponseV2(PersonDatabase personDatabase);

}
