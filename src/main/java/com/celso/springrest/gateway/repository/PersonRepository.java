package com.celso.springrest.gateway.repository;

import com.celso.springrest.gateway.model.PersonDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT p FROM person p WHERE p.firstName LIKE LOWER(CONCAT('%',:firstName,'%'))")
    Page<PersonDatabase> findPersonDatabaseByFirstName(@Param("firstName") String firstName, Pageable pageable);
}
