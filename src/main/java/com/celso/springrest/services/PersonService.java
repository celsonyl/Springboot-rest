package com.celso.springrest.services;

import com.celso.springrest.controller.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PersonService {

    private final AtomicLong counter = new AtomicLong();

    public Person findById(String id) {
        Person obj = new Person();
        obj.setId(counter.incrementAndGet());
        obj.setFirstName("Celso");
        obj.setLastName("Bertolotto");
        obj.setAddress("Octavio casemiro");
        obj.setGender("Masculino");

        return obj;
    }

    public List<Person> findAll() {
        List<Person> personList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Person person = mockPerson(i);
            personList.add(person);
        }
        return personList;
    }

    private Person mockPerson(int i) {
        Person obj = new Person();
        obj.setId(counter.incrementAndGet());
        obj.setFirstName("Celso " + i);
        obj.setLastName("Bertolotto " + i);
        obj.setAddress("Octavio casemiro " + i);
        obj.setGender("Masculino " + i);

        return obj;
    }
}