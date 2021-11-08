package com.celso.springrest.controller;

import com.celso.springrest.controller.model.BookRequest;
import com.celso.springrest.controller.model.BookResponse;
import com.celso.springrest.services.BookService;
import com.celso.springrest.translator.BookMapperImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "Book EndPoint", tags = "Book endpoint")
@RestController
@RequestMapping(value = "/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @ApiOperation(value = "Create book")
    @PostMapping(produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<Void> createBook(@RequestBody BookRequest bookRequest, UriComponentsBuilder uriComponentsBuilder) {
        var bookDomain = bookService.createBook(bookRequest);
        var uri = uriComponentsBuilder.path("/{id}").buildAndExpand(bookDomain.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @ApiOperation(value = "Get Book by id")
    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        var bookDomain = bookService.getBookById(id);
        return ResponseEntity.ok().body(new BookMapperImpl().bookDomainToResponse(bookDomain));
    }

    @ApiOperation(value = "Get all books")
    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        var listBooks = bookService.getAllBooks();

        return ResponseEntity.ok().body(listBooks.stream()
                .map(new BookMapperImpl()::bookDomainToResponse)
                .collect(Collectors.toList()));
    }

    @ApiOperation(value = "Update book")
    @PutMapping(value = "{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<Void> updateBook(@RequestBody @Valid BookRequest bookRequest, @PathVariable String id) {
        bookService.updateBook(bookRequest, id);
        return ResponseEntity.noContent().build();
    }
}