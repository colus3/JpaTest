package com.example.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by colus on 2017. 2. 6..
 */
public interface ReadOnlyMemberRepository extends JpaRepository<ReadOnlyMember, Long> {
    ReadOnlyMember findByUsername(String username);
}
