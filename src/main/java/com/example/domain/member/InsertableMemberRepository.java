package com.example.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by colus on 2017. 2. 6..
 */
public interface InsertableMemberRepository extends JpaRepository<InsertableMember, Long> {
    InsertableMember findByUsername(String username);
}
