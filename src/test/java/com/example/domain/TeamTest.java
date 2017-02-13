package com.example.domain;

import com.example.domain.member.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

/**
 * Created by colus on 2017. 2. 13..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("none")
@SpringBootTest
public class TeamTest {

    @Autowired
    private TeamRepository teamRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testCascadePersist() {

        final String userName = "userName2";

        Team team = new Team("Team1");
        Member member = new Member(userName, team);
        team.getMembers().add(member);

        teamRepository.save(team);

        assertNotNull(teamRepository.findByName("Team1"));
        assertEquals(userName, teamRepository.findByName("Team1").getMembers().get(0).getUsername());
    }
}