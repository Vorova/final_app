package com.vorova.final_app.repository;

import com.vorova.final_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select user from User as user join fetch user.roles where user.email = :name")
    Optional<User> findUserByEmail(String name);
}