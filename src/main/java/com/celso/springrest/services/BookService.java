package com.celso.springrest.services;

import com.celso.springrest.controller.model.BookRequest;
import com.celso.springrest.domain.BookDomain;
import com.celso.springrest.gateway.repository.BookRespository;
import com.celso.springrest.translator.BookMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRespository bookRespository;

    public BookDomain createBook(BookRequest bookRequest) {
        var bookDomain = new BookMapperImpl().bookRequestToDomain(bookRequest);
        var bookDatabase = bookRespository.save(new BookMapperImpl().bookDomainToDatabase(bookDomain));

        return new BookMapperImpl().bookDatabaseToDomain(bookDatabase);
    }

    public List<BookDomain> getAllBooks() {
        var listBookDatabase = bookRespository.findAll();

        return listBookDatabase.stream()
                .map(new BookMapperImpl()::bookDatabaseToDomain)
                .collect(Collectors.toList());
    }
}