package com.celso.springrest.gateway.repository;

import com.celso.springrest.gateway.model.PersonDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<PersonDatabase, Long> {
}
