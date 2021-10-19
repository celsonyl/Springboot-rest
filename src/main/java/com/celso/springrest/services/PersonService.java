package com.celso.springrest.services;

import com.celso.springrest.controller.model.PersonRequest;
import com.celso.springrest.controller.model.PersonRequestV2;
import com.celso.springrest.controller.model.PersonResponse;
import com.celso.springrest.controller.model.PersonResponseV2;
import com.celso.springrest.exceptions.ObjectNotFound;
import com.celso.springrest.gateway.model.PersonDatabase;
import com.celso.springrest.gateway.repository.PersonRepository;
import com.celso.springrest.translator.PersonMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public PersonResponse findById(String id) {
        var obj = personRepository.findById(Long.parseLong(id));
        if (obj.isEmpty()) {
            throw new ObjectNotFound("Usuario não existe!");
        }

        return new PersonMapperImpl().personDatabaseToResponse(obj.get());
    }

    public List<PersonResponse> findAll() {
        var listPerson = personRepository.findAll();

        return listPerson.stream()
                .map(new PersonMapperImpl()::personDatabaseToResponse)
                .collect(Collectors.toList());
    }

    public PersonResponse createPerson(PersonRequest obj) {
        var personSaved = personRepository.save(new PersonMapperImpl().personRequestToDatabase(obj));
        return new PersonMapperImpl().personDatabaseToResponse(personSaved);
    }

    public PersonResponseV2 createPersonV2(PersonRequestV2 obj) {
        var personSaved = personRepository.save(new PersonMapperImpl().personRequestV2ToDatabase(obj));
        return new PersonMapperImpl().personDatabaseToResponseV2(personSaved);
    }

    public void updatePerson(PersonRequest obj, String id) {
        var personMapper = new PersonMapperImpl();
        var person = findById(id);
        updatePerson(personMapper.personResponseToDatabase(person), personMapper.personRequestToDatabase(obj));
    }

    public void deletePerson(String id) {
        var person = findById(id);
        personRepository.deleteById(new PersonMapperImpl().personResponseToDatabase(person).getId());
    }

    private void updatePerson(PersonDatabase person, PersonDatabase obj) {
        person.setFirstName(obj.getFirstName());
        person.setLastName(obj.getLastName());
        person.setGender(obj.getGender());
        person.setAddress(obj.getAddress());
        personRepository.save(person);
    }
}