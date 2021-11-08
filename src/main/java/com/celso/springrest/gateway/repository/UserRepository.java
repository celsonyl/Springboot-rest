package com.celso.springrest.gateway.repository;

import com.celso.springrest.gateway.model.UserDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDatabase, Long> {

    @Query("SELECT u FROM user u WHERE u.username =: username")
    UserDatabase findByUsername(@Param("username") String username);
}
