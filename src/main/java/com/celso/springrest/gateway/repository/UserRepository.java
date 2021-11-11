package com.celso.springrest.gateway.repository;

import com.celso.springrest.gateway.model.UserDatabase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDatabase, Long> {

    @Query(value = "SELECT u FROM users u WHERE u.username =:userName")
    UserDatabase findByUsername(@Param("userName") String userName);
}
