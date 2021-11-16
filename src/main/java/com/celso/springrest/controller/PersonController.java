package com.celso.springrest.controller;

import com.celso.springrest.controller.model.PersonRequest;
import com.celso.springrest.controller.model.PersonRequestV2;
import com.celso.springrest.controller.model.PersonResponse;
import com.celso.springrest.services.PersonService;
import com.celso.springrest.translator.PersonMapperImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@Api(value = "Person EndPoint", tags = "Person endpoint")
@RestController
@RequestMapping(value = "/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    @ApiOperation(value = "Get Person by id")
    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<PersonResponse> findById(@PathVariable String id) {
        var obj = personService.findById(id);
        return ResponseEntity.ok().body(new PersonMapperImpl().personDomainToResponse(obj));
    }

    @ApiOperation(value = "Get all persons")
    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<List<PersonResponse>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "12") int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        var listPersonDomain = personService.findAll(pageable);
        return ResponseEntity.ok().body(listPersonDomain.stream()
                .map(new PersonMapperImpl()::personDomainToResponse)
                .collect(Collectors.toList()));
    }

    @ApiOperation(value = "Create Person")
    @PostMapping(produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<Void> createPerson(@RequestBody PersonRequest obj, UriComponentsBuilder uriComponentsBuilder) {
        var person = personService.createPerson(obj);
        return ResponseEntity.created(uriComponentsBuilder.path("person/{id}").buildAndExpand(person.getId()).toUri()).build();
    }

    @ApiOperation(value = "Create Person v2")
    @PostMapping(value = "/v2", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<Void> createPersonV2(@RequestBody PersonRequestV2 obj, UriComponentsBuilder uriComponentsBuilder) {
        var person = personService.createPersonV2(obj);
        return ResponseEntity.created(uriComponentsBuilder.path("person/{id}").buildAndExpand(person.getId()).toUri()).build();
    }

    @ApiOperation(value = "Update Person")
    @PutMapping(value = "/{id}", produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<Void> updatePerson(@PathVariable String id, @RequestBody PersonRequest obj) {
        personService.updatePerson(obj, id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Delete person by id")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable String id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Disable person by id")
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> disablePerson(@PathVariable Long id) {
        personService.disablePerson(id);
        return ResponseEntity.noContent().build();
    }
}