package com.celso.springrest.services;

import com.celso.springrest.controller.model.BookRequest;
import com.celso.springrest.domain.BookDomain;
import com.celso.springrest.exceptions.handler.ObjectNotFound;
import com.celso.springrest.gateway.model.BookDatabase;
import com.celso.springrest.gateway.repository.BookRepository;
import com.celso.springrest.translator.BookMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public BookDomain createBook(BookRequest bookRequest) {
        var bookDomain = new BookMapperImpl().bookRequestToDomain(bookRequest);
        var bookDatabase = bookRepository.save(new BookMapperImpl().bookDomainToDatabase(bookDomain));

        return new BookMapperImpl().bookDatabaseToDomain(bookDatabase);
    }

    public BookDomain getBookById(Long id) {
        var bookDatabase = bookRepository.findById(id);
        if (bookDatabase.isEmpty()) {
            throw new ObjectNotFound("Book not found!");
        }

        return new BookMapperImpl().bookDatabaseToDomain(bookDatabase.get());
    }

    public List<BookDomain> getAllBooks() {
        var listBookDatabase = bookRepository.findAll();

        return listBookDatabase.stream()
                .map(new BookMapperImpl()::bookDatabaseToDomain)
                .collect(Collectors.toList());
    }

    public void updateBook(BookRequest bookRequest, String id) {
        var bookMapper = new BookMapperImpl();
        var bookDomain = getBookById(Long.parseLong(id));
        updateBook(bookMapper.bookDomainToDatabase(bookDomain), bookMapper.bookRequestToDomain(bookRequest));
    }

    private void updateBook(BookDatabase bookDatabase, BookDomain bookDomain) {
        bookDatabase.setAuthor(bookDomain.getAuthor());
        bookDatabase.setLaunch_date(bookDomain.getLaunch_date());
        bookDatabase.setPrice(bookDomain.getPrice());
        bookDatabase.setTitle(bookDomain.getTitle());
        bookRepository.save(bookDatabase);
    }
}