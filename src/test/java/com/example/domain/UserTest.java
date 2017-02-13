package com.example.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("none")
@SpringBootTest
/**
 * Created by colus on 2017. 2. 13..
 */
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void userInsert() throws Exception {

        User user = new User("Donghwan", "Lee", "01020419909");
        userRepository.save(user);
    }

    @Test
    public void userUpdate() throws Exception {

        Long id = 1L;
        User user = userRepository.findOne(id);

        user.setFirstName("Dong");
        userRepository.save(user);
    }

    @Test
    public void userDelete() throws Exception {

        User user = userRepository.save(new User("Donghwan", "Kim", "01011112222"));

        System.out.println(user.getFirstName() + " " + user.getLastName());

        userRepository.delete(user.getId());
    }

    @Test
    public void userSearch() throws Exception {

        User user = userRepository.save(new User("Donghwan", "Choi", "01033334444"));

        String lastName = "Choi";

        User choi = userRepository.findByLastName(lastName);

        System.out.println(choi.getFirstName() + " " + choi.getLastName());

    }

}