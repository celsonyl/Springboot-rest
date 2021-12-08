package com.celso.springrest.controller;

import com.celso.springrest.controller.model.BookRequest;
import com.celso.springrest.controller.model.BookResponse;
import com.celso.springrest.services.BookService;
import com.celso.springrest.translator.BookMapperImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;

@Api(value = "Book EndPoint", tags = "Book endpoint")
@RestController
@RequestMapping(value = "/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @ApiOperation(value = "Create book")
    @PostMapping(produces = {"application/json", "application/xml"}, consumes = {"application/json", "application/xml", "application/x-yaml"})
    @CrossOrigin
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
    @CrossOrigin
    public ResponseEntity<Page<BookResponse>> getAllBooks(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "12") Integer limit,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        var sortDirection = "DESC".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "author"));

        var listBooks = bookService.getAllBooks(pageable);
        var listBooksResponse = listBooks.map(new BookMapperImpl()::bookDomainToResponse);

        return ResponseEntity.ok(listBooksResponse);
    }

    @ApiOperation(value = "Get all books by Author name")
    @GetMapping(value = "/authorName/{author}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<Page<BookResponse>> getAllBooksByAuthorName(
            @PathVariable(value = "author") String author,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "12") Integer limit,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        var sortDirection = "DESC".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "author"));

        var listBooks = bookService.getAllBooksByAuthorName(author, pageable);
        var listBooksResponse = listBooks.map(new BookMapperImpl()::bookDomainToResponse);

        return ResponseEntity.ok(listBooksResponse);
    }

    @ApiOperation(value = "Update book")
    @PutMapping(value = "{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<Void> updateBook(@RequestBody @Valid BookRequest bookRequest, @PathVariable String id) {
        bookService.updateBook(bookRequest, id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Delete book by id")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}