package com.celso.springrest.services;

import com.celso.springrest.controller.model.Person;
import com.celso.springrest.exceptions.ObjectNotFound;
import com.celso.springrest.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person findById(String id) {
        var obj = personRepository.findById(Long.parseLong(id));
        if (obj.isEmpty()) {
            throw new ObjectNotFound("Usuario não existe!");
        }

        return obj.get();
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person createPerson(Person obj) {
        return personRepository.save(obj);
    }

    public void updatePerson(Person obj, String id) {
        var person = findById(id);
        updatePerson(person, obj);
    }

    public void deletePerson(String id) {
        var person = findById(id);
        personRepository.deleteById(person.getId());
    }

    private void updatePerson(Person person, Person obj) {
        person.setFirstName(obj.getFirstName());
        person.setLastName(obj.getLastName());
        person.setGender(obj.getGender());
        person.setAddress(obj.getAddress());
        personRepository.save(person);
    }

}