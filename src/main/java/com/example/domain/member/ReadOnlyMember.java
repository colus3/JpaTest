package com.example.domain.member;

import com.example.domain.Team;

import javax.persistence.*;
import java.util.ArrayList;

/**
 * Created by colus on 2017. 1. 12..
 */
@Entity
@Table(name="MEMBER")
public class ReadOnlyMember {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
    private Team team;

    public ReadOnlyMember() {
    }

    public ReadOnlyMember(String username, Team team) {
        this.username = username;
        this.team = team;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
        team.getReadOnlyMembers().add(this);
    }

    public String toString() {
        return String.format("%d : %s : %s", id, username, team.getName());
    }
}
