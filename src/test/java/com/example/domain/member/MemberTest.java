package com.example.domain.member;

import com.example.domain.Team;
import com.example.domain.TeamRepository;
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
public class MemberTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testCascadePersist() {

        final String userName = "userName";

        Team team = new Team();
        team.setName("TeamAA");

        Member member = new Member();
        member.setUsername(userName);
        member.setTeam(team);
        memberRepository.save(member);

        assertNotNull(memberRepository.findByUsername(userName));
        assertEquals(userName, memberRepository.findByUsername(userName).getUsername());
        // member에 등록도 되었고, 새로운 team은 추가 되었음.
        assertNotNull(memberRepository.findByUsername(userName).getTeam());
        assertNotNull(teamRepository.findByName("TeamAA").getId());
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testCascadeRemove() {

        final String userName = "userName";

        Member member = memberRepository.findByUsername(userName);

        memberRepository.delete(member.getId());

        assertNull(memberRepository.findByUsername(userName));
        assertNull(teamRepository.findByName("TeamAA"));

    }


    @Test
    public void testCascadeDetach() {

        final String userName = "userName1";

        Team teamAAA = new Team("TeamAAA");
        Member member = new Member(userName, teamAAA);

        memberRepository.save(member);

        member.setUsername("userNameAA");
        teamAAA.setName("TeamBBB");

        assertEquals(userName, memberRepository.findByUsername(userName).getUsername());
        assertEquals("TeamAAA", teamRepository.findByName("TeamAAA").getName());
    }

    @Test
    @Transactional
    @Rollback
    public void testCascadeDetachWithTransactional() {

        final String userName = "userName1";

        Team teamAAA = new Team("TeamAAA");
        Member member = new Member(userName, teamAAA);

        memberRepository.save(member);

        member.setUsername("userNameAA");
        teamAAA.setName("TeamBBB");

        assertEquals("userNameAA", memberRepository.findByUsername("userNameAA").getUsername());
        assertEquals("TeamBBB", teamRepository.findByName("TeamBBB").getName());
    }
}