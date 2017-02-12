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
public class UpdatableMemberTest {

    @Inject
    private TeamRepository teamRepository;

    @Inject
    private MemberRepository memberRepository;

    @Inject
    private UpdatableMemberRepository updatableMemberRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testInsertMember() throws Exception {

        final String userName = "member1";

        teamRepository.save(new Team("TeamA"));
        teamRepository.save(new Team("TeamB"));

        Team teamA = teamRepository.findByName("TeamA");

        UpdatableMember member = new UpdatableMember();
        member.setUsername(userName);
        member.setTeam(teamA);
        updatableMemberRepository.save(member);

        assertNotNull(updatableMemberRepository.findByUsername(userName));
        assertEquals(userName, updatableMemberRepository.findByUsername(userName).getUsername());
        assertNotNull(updatableMemberRepository.findByUsername(userName).getTeam());
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

        UpdatableMember member = updatableMemberRepository.findByUsername(userName);
        member.setUsername(userName);
        member.setTeam(teamC);
        updatableMemberRepository.save(member);

        assertEquals(userName, member.getUsername());
        assertNotNull(member.getTeam());
        assertNotEquals(teamA.getId(), updatableMemberRepository.findByUsername(userName).getTeam().getId());
        assertEquals(teamC.getId(), updatableMemberRepository.findByUsername(userName).getTeam().getId());
    }

    @Test
    @Transactional
    public void testDeleteMember() throws Exception {

        final String userName = "member3";

        teamRepository.save(new Team("TeamA"));
        teamRepository.save(new Team("TeamB"));

        Team teamA = teamRepository.findByName("TeamA");
        memberRepository.save(new Member(userName, teamA));

        UpdatableMember member = updatableMemberRepository.findByUsername(userName);

        updatableMemberRepository.delete(member);

        assertNull(updatableMemberRepository.findByUsername(userName));
    }

    @Test
    public void testInsertMemberUsingCascade() throws Exception {

        final String userName = "member4";

        teamRepository.save(new Team("TeamA"));
        teamRepository.save(new Team("TeamB"));

        Team teamD = new Team("TeamD");

        UpdatableMember member = new UpdatableMember();
        member.setUsername(userName);
        member.setTeam(teamD);
        updatableMemberRepository.save(member);

        assertNotNull(updatableMemberRepository.findByUsername(userName));
        assertEquals(userName, updatableMemberRepository.findByUsername(userName).getUsername());
        // member에 등록은 안되지만 새로운 team은 추가 되었음.
        assertNull(updatableMemberRepository.findByUsername(userName).getTeam());
        assertNotNull(teamRepository.findByName("TeamD").getId());
    }
}