package com.example.domain.member;

import com.example.domain.Team;
import com.example.domain.TeamRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.inject.Inject;

import static org.junit.Assert.*;

/**
 * Created by colus on 2017. 2. 12..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("create")
@SpringBootTest
@WebAppConfiguration
public class InsertableMemberTest {

    @Inject
    private TeamRepository teamRepository;

    @Inject
    private MemberRepository memberRepository;

    @Inject
    private InsertableMemberRepository insertableMemberRepository;

    @Test
    public void testInsertMember() throws Exception {

        final String userName = "member1";

        teamRepository.save(new Team("TeamA"));
        teamRepository.save(new Team("TeamB"));

        Team teamA = teamRepository.findByName("TeamA");

        InsertableMember member = new InsertableMember();
        member.setUsername(userName);
        member.setTeam(teamA);
        insertableMemberRepository.save(member);

        assertNotNull(insertableMemberRepository.findByUsername(userName));
        assertEquals(userName, insertableMemberRepository.findByUsername(userName).getUsername());
        assertNotNull(insertableMemberRepository.findByUsername(userName).getTeam());
        assertEquals(teamA.getId(), insertableMemberRepository.findByUsername(userName).getTeam().getId());
    }

    @Test
    public void testUpdateMember() throws Exception {

        final String userName = "member2";

        teamRepository.save(new Team("TeamA"));
        teamRepository.save(new Team("TeamB"));

        Team teamA = teamRepository.findByName("TeamA");
        memberRepository.save(new Member(userName, teamA));

        Team teamC = teamRepository.save(new Team("TeamC"));

        InsertableMember member = insertableMemberRepository.findByUsername(userName);
        member.setUsername(userName);
        member.setTeam(teamC);
        insertableMemberRepository.save(member);

        assertEquals(userName, insertableMemberRepository.findByUsername(userName).getUsername());
        assertNotNull(insertableMemberRepository.findByUsername(userName).getTeam());
        assertEquals(teamA.getId(), insertableMemberRepository.findByUsername(userName).getTeam().getId());
        assertNotEquals(teamC.getId(), insertableMemberRepository.findByUsername(userName).getTeam().getId());
    }

    @Test
    public void testDeleteMember() throws Exception {

        final String userName = "member3";

        teamRepository.save(new Team("TeamA"));
        teamRepository.save(new Team("TeamB"));

        Team teamA = teamRepository.findByName("TeamA");
        memberRepository.save(new Member(userName, teamA));

        InsertableMember member = insertableMemberRepository.findByUsername(userName);

        insertableMemberRepository.delete(member);

        assertNull(insertableMemberRepository.findByUsername(userName));
    }

    @Test
    public void testInsertMemberUsingCascade() throws Exception {

        final String userName = "member4";

        teamRepository.save(new Team("TeamA"));
        teamRepository.save(new Team("TeamB"));

        Team teamD = new Team("TeamD");

        InsertableMember member = new InsertableMember();
        member.setUsername(userName);
        member.setTeam(teamD);
        insertableMemberRepository.save(member);

        assertNotNull(insertableMemberRepository.findByUsername(userName));
        assertEquals(userName, insertableMemberRepository.findByUsername(userName).getUsername());
        // member에 등록도 되었고, 새로운 team은 추가 되었음.
        assertNotNull(insertableMemberRepository.findByUsername(userName).getTeam());
        assertNotNull(teamRepository.findByName("TeamD").getId());
    }
}