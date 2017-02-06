package com.example.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by colus on 2017. 2. 6..
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUsername(String username);
}
