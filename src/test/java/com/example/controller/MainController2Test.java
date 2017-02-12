package com.example.controller;

import com.example.domain.member.Member;
import com.example.domain.member.MemberRepository;
import com.example.domain.Team;
import com.example.domain.TeamRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

/**
 * Created by colus on 2017. 1. 12..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("create")
@SpringBootTest
public class MainController2Test {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Before
    public void setUp() throws Exception {
        teamRepository.save(new Team("Red"));
        teamRepository.save(new Team("Blue"));
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testMemberInsert() throws Exception {

        Team blueTeam = teamRepository.findByName("Blue");
        System.out.println(blueTeam.getName());
        Member member = new Member();
        member.setUsername("Donghwan Lee");
        member.setTeam(blueTeam);
        memberRepository.save(member);

        assertEquals(member.getUsername(), "Donghwan Lee");
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testTeamInsert() throws Exception {

        Team green = new Team("Green");
        teamRepository.save(green);

        assertEquals("Green", teamRepository.findByName("Green").getName());
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testMemberInsertByTeam() throws Exception {

        Team green = new Team("Green");
        Member member = new Member("Donghwan Choi", green);
        green.getMembers().add(member);
        teamRepository.save(green);

        assertEquals("Donghwan Choi", green.getMembers().get(0).getUsername());
        Member donghwanChoi = memberRepository.findByUsername("Donghwan Choi");

        assertNull(donghwanChoi);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void testTeamInsertByMember() throws Exception {

        Team yellow = new Team("Yellow");
        teamRepository.save(yellow);
        Member member = new Member("Donghwan Lee", yellow);
        yellow.getMembers().add(member);
        memberRepository.save(member);

        Team yellowTeam = teamRepository.findByName("Yellow");
        assertNotNull(yellowTeam);
        assertEquals("Yellow", yellowTeam.getName());
    }
}