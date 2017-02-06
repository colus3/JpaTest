package com.example.controller;

import com.example.domain.*;
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
import java.util.Arrays;
import java.util.List;

/**
 * Created by colus on 2017. 1. 12..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("create")
@SpringBootTest
@WebAppConfiguration
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
    public void testMemberSearch() throws Exception {

        Team team = teamRepository.findByName("Blue");

        memberRepository.save(new Member("Donghwan Lee", team));

        Member donghwanLee = memberRepository.findByUsername("Donghwan Lee");


        System.out.println(donghwanLee.getId() + " " + donghwanLee.getUsername() + " : " + team.getName());

    }

    @Test
    public void testTeamInsert() throws Exception {

        Team green = new Team("Green");
        teamRepository.save(green);

        System.out.println("team : " + green.getName());

        Member donghwanChoi = new Member("Donghwan Choi", green);

        memberRepository.save(donghwanChoi);

        Member member = memberRepository.findByUsername("Donghwan Choi");

        System.out.println(member.getUsername() + " : " + member.getTeam().getName());


    }
}