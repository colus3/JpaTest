package com.example.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by colus on 2017. 2. 6..
 */
public interface UpdatableMemberRepository extends JpaRepository<UpdatableMember, Long> {
    UpdatableMember findByUsername(String username);
}
