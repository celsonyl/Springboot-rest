package com.celso.springrest.controller;

import com.celso.springrest.controller.model.PersonRequest;
import com.celso.springrest.controller.model.PersonRequestV2;
import com.celso.springrest.controller.model.PersonResponse;
import com.celso.springrest.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping(value = "/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<PersonResponse> findById(@PathVariable String id) {
        var obj = personService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @GetMapping
    public ResponseEntity<List<PersonResponse>> findAll() {
        return ResponseEntity.ok().body(personService.findAll());
    }

    @PostMapping
    public ResponseEntity<Void> createPerson(@RequestBody PersonRequest obj, UriComponentsBuilder uriComponentsBuilder) {
        var person = personService.createPerson(obj);
        return ResponseEntity.created(uriComponentsBuilder.path("person/{id}").buildAndExpand(person.getId()).toUri()).build();
    }

    @PostMapping("/v2")
    public ResponseEntity<Void> createPersonV2(@RequestBody PersonRequestV2 obj, UriComponentsBuilder uriComponentsBuilder) {
        var person = personService.createPersonV2(obj);
        return ResponseEntity.created(uriComponentsBuilder.path("person/{id}").buildAndExpand(person.getId()).toUri()).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> updatePerson(@PathVariable String id, @RequestBody PersonRequest obj) {
        personService.updatePerson(obj, id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable String id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }
}