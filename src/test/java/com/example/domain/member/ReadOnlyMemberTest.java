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
import javax.transaction.Transactional;

import static org.junit.Assert.*;

/**
 * Created by colus on 2017. 2. 12..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("create")
@SpringBootTest
@WebAppConfiguration
@Rollback
public class ReadOnlyMemberTest {

    @Inject
    private TeamRepository teamRepository;

    @Inject
    private MemberRepository memberRepository;

    @Inject
    private ReadOnlyMemberRepository readOnlyMemberRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testInsertMember() throws Exception {

        final String userName = "member1";

        teamRepository.save(new Team("TeamA"));
        teamRepository.save(new Team("TeamB"));

        Team teamA = teamRepository.findByName("TeamA");

        ReadOnlyMember member = new ReadOnlyMember();
        member.setUsername(userName);
        member.setTeam(teamA);
        readOnlyMemberRepository.save(member);

        assertNotNull(readOnlyMemberRepository.findByUsername(userName));
        assertEquals(userName, readOnlyMemberRepository.findByUsername(userName).getUsername());
        assertNotNull(readOnlyMemberRepository.findByUsername(userName).getTeam());
        assertEquals(teamA.getId(), member.getTeam().getId());
    }

    @Test
    public void testUpdateMember() throws Exception {

        final String userName = "member2";

        teamRepository.save(new Team("TeamA"));
        teamRepository.save(new Team("TeamB"));

        Team teamA = teamRepository.findByName("TeamA");
        memberRepository.save(new Member(userName, teamA));

        Team teamC = teamRepository.save(new Team("TeamC"));

        ReadOnlyMember member = readOnlyMemberRepository.findByUsername(userName);
        member.setUsername(userName);
        member.setTeam(teamC);
        readOnlyMemberRepository.save(member);

        assertEquals(userName, member.getUsername());
        assertNotNull(member.getTeam());
        assertEquals(teamA.getId(), readOnlyMemberRepository.findByUsername(userName).getTeam().getId());
        assertNotEquals(teamC.getId(), readOnlyMemberRepository.findByUsername(userName).getTeam().getId());
    }

    @Test
    public void testDeleteMember() throws Exception {

        final String userName = "member3";

        teamRepository.save(new Team("TeamA"));
        teamRepository.save(new Team("TeamB"));

        Team teamA = teamRepository.findByName("TeamA");
        memberRepository.save(new Member(userName, teamA));

        ReadOnlyMember member = readOnlyMemberRepository.findByUsername(userName);

        readOnlyMemberRepository.delete(member);

        assertNull(readOnlyMemberRepository.findByUsername(userName));
    }

    @Test
    public void testInsertMemberUsingCascade() throws Exception {

        final String userName = "member4";

        teamRepository.save(new Team("TeamA"));
        teamRepository.save(new Team("TeamB"));

        Team teamD = new Team("TeamD");

        ReadOnlyMember member = new ReadOnlyMember();
        member.setUsername(userName);
        member.setTeam(teamD);
        readOnlyMemberRepository.save(member);

        assertNotNull(readOnlyMemberRepository.findByUsername(userName));
        assertEquals(userName, readOnlyMemberRepository.findByUsername(userName).getUsername());
        // member에 등록은 안되지만 새로운 team은 추가 되었음.
        assertNull(readOnlyMemberRepository.findByUsername(userName).getTeam());
        assertNotNull(teamRepository.findByName("TeamD").getId());
    }
}