package com.celso.springrest.gateway.repository;

import com.celso.springrest.gateway.model.BookDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRespository extends JpaRepository<BookDatabase, Long> {
}
