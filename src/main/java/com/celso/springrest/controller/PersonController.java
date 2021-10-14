package com.celso.springrest.controller;

import com.celso.springrest.controller.model.Person;
import com.celso.springrest.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> findById(@PathVariable String id) {
        var obj = personService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    public ResponseEntity<List<Person>> findAll() {
        return ResponseEntity.ok().body(personService.findAll());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> createPerson(@RequestBody Person obj, UriComponentsBuilder uriComponentsBuilder) {
        var person = personService.createPerson(obj);
        return ResponseEntity.created(uriComponentsBuilder.path("person/{id}").buildAndExpand(person.getId()).toUri()).build();
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> updatePerson(@PathVariable String id, @RequestBody Person obj) {
        personService.updatePerson(obj, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable String id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}