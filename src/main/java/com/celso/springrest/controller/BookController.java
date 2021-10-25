package com.celso.springrest.controller;

import com.celso.springrest.controller.model.BookRequest;
import com.celso.springrest.controller.model.BookResponse;
import com.celso.springrest.services.BookService;
import com.celso.springrest.translator.BookMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping(produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<Void> createBook(@RequestBody BookRequest bookRequest, UriComponentsBuilder uriComponentsBuilder) {
        var bookDomain = bookService.createBook(bookRequest);
        var uri = uriComponentsBuilder.path("/{id}").buildAndExpand(bookDomain.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        var bookDomain = bookService.getBookById(id);
        return ResponseEntity.ok().body(new BookMapperImpl().bookDomainToResponse(bookDomain));
    }

    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        var listBooks = bookService.getAllBooks();

        return ResponseEntity.ok().body(listBooks.stream()
                .map(new BookMapperImpl()::bookDomainToResponse)
                .collect(Collectors.toList()));
    }
}