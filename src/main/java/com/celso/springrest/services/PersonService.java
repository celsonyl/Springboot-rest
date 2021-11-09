package com.celso.springrest.services;

import com.celso.springrest.controller.model.PersonRequest;
import com.celso.springrest.controller.model.PersonRequestV2;
import com.celso.springrest.controller.model.PersonResponseV2;
import com.celso.springrest.domain.PersonDomain;
import com.celso.springrest.exceptions.handler.ObjectNotFound;
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

    public PersonDomain findById(String id) {
        var obj = personRepository.findById(Long.parseLong(id));
        if (obj.isEmpty()) {
            throw new ObjectNotFound("Usuario não existe!");
        }
        return new PersonMapperImpl().personDatabaseToDomain(obj.get());
    }

    public List<PersonDomain> findAll() {
        var listPerson = personRepository.findAll();

        return listPerson.stream()
                .map(new PersonMapperImpl()::personDatabaseToDomain)
                .collect(Collectors.toList());
    }

    public PersonDomain createPerson(PersonRequest obj) {
        var personDomain = new PersonMapperImpl().personRequestToDomain(obj);
        var personSaved = personRepository.save(new PersonMapperImpl().personDomainToDatabase(personDomain));

        return new PersonMapperImpl().personDatabaseToDomain(personSaved);
    }

    public PersonResponseV2 createPersonV2(PersonRequestV2 obj) {
        var personSaved = personRepository.save(new PersonMapperImpl().personRequestV2ToDatabase(obj));
        return new PersonMapperImpl().personDatabaseToResponseV2(personSaved);
    }

    public void updatePerson(PersonRequest obj, String id) {
        var personMapper = new PersonMapperImpl();
        var person = findById(id);
        updatePerson(personMapper.personDomainToDatabase(person), personMapper.personRequestToDatabase(obj));
    }

    public void deletePerson(String id) {
        var person = findById(id);
        personRepository.deleteById(new PersonMapperImpl().personDomainToDatabase(person).getId());
    }

    private void updatePerson(PersonDatabase person, PersonDatabase obj) {
        person.setFirstName(obj.getFirstName());
        person.setLastName(obj.getLastName());
        person.setGender(obj.getGender());
        person.setAddress(obj.getAddress());
        personRepository.save(person);
    }
}