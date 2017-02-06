package com.example.controller;

import com.example.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.List;

/**
 * Created by colus on 2017. 1. 12..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("update")
@SpringBootTest
@WebAppConfiguration
public class MainController2Test {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void testMemberSearch() throws Exception {

//        teamRepository.save(new Team("blue"));

        Team team = teamRepository.findByName("blue");

        Member donghwanLee = memberRepository.findByUsername("Donghwan Lee");

        donghwanLee.setTeam(team);

        System.out.println(donghwanLee.getUsername() + " " + donghwanLee.getTeam().getName());

    }

    @Test
    public void testTeamInsert() throws Exception {
        Team team = teamRepository.findByName("blue");

        Member member = memberRepository.findByUsername("Donghwan Lee");

        team.setMembers(Arrays.asList(member));

    }
}