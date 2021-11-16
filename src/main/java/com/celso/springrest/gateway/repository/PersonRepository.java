package com.celso.springrest.gateway.repository;

import com.celso.springrest.gateway.model.PersonDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<PersonDatabase, Long> {

    @Modifying
    @Query(value = "UPDATE person p SET p.enabled = false WHERE p.id =:id")
    void disablePerson(@Param("id") Long id);
}
