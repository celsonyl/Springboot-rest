package com.celso.springrest.gateway.repository;

import com.celso.springrest.gateway.model.BookDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<BookDatabase, Long> {

    @Query("SELECT p FROM books p WHERE p.author LIKE LOWER(CONCAT('%',:author,'%'))")
    Page<BookDatabase> findBookDatabaseByAuthor(@Param("author") String author, Pageable pageable);
}