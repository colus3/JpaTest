package com.example.controller;

import com.example.domain.member.MemberRepository;
import com.example.domain.TeamRepository;
import com.example.domain.User;
import com.example.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by colus on 2017. 1. 12..
 */
@Controller
@RequestMapping("/")
public class MainController {

    @Autowired private UserRepository userRepository;
    @Autowired private MemberRepository memberRepository;
    @Autowired private TeamRepository teamRepository;

    @RequestMapping
    @ResponseBody
    public List<User> index() {
        return userRepository.findAll();
    }
}
