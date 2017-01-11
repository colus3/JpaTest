package com.example.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by colus on 2017. 1. 12..
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLastName(String lastName);
}
