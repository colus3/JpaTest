package com.example.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by colus on 2017. 2. 6..
 */
public interface TeamRepository extends JpaRepository<Team, Long> {
    Team findByName(String red);
}
