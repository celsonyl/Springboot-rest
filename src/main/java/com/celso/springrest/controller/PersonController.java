package com.celso.springrest.controller;

import com.celso.springrest.controller.model.PersonRequest;
import com.celso.springrest.controller.model.PersonRequestV2;
import com.celso.springrest.controller.model.PersonResponse;
import com.celso.springrest.services.PersonService;
import com.celso.springrest.translator.PersonMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<PersonResponse> findById(@PathVariable String id) {
        var obj = personService.findById(id);
        return ResponseEntity.ok().body(new PersonMapperImpl().personDomainToResponse(obj));
    }

    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<List<PersonResponse>> findAll() {
        var listPersonDomain = personService.findAll();
        return ResponseEntity.ok().body(listPersonDomain.stream()
                .map(new PersonMapperImpl()::personDomainToResponse)
                .collect(Collectors.toList()));
    }

    @PostMapping(produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<Void> createPerson(@RequestBody PersonRequest obj, UriComponentsBuilder uriComponentsBuilder) {
        var person = personService.createPerson(obj);
        return ResponseEntity.created(uriComponentsBuilder.path("person/{id}").buildAndExpand(person.getId()).toUri()).build();
    }

    @PostMapping(value = "/v2", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<Void> createPersonV2(@RequestBody PersonRequestV2 obj, UriComponentsBuilder uriComponentsBuilder) {
        var person = personService.createPersonV2(obj);
        return ResponseEntity.created(uriComponentsBuilder.path("person/{id}").buildAndExpand(person.getId()).toUri()).build();
    }

    @PutMapping(value = "/{id}", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
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