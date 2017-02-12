package com.example.domain;

import com.example.domain.member.InsertableMember;
import com.example.domain.member.Member;
import com.example.domain.member.ReadOnlyMember;
import com.example.domain.member.UpdatableMember;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by colus on 2017. 2. 6..
 */
@Entity
@Table(name = "TEAM")
public class Team {
    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    private List<InsertableMember> insertableMembers = new ArrayList<>();

    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    private List<UpdatableMember> updatableMembers = new ArrayList<>();

    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    private List<ReadOnlyMember> readOnlyMembers = new ArrayList<>();

    public Team() {}

    public Team(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public List<InsertableMember> getInsertableMembers() {
        return insertableMembers;
    }

    public void setInsertableMembers(List<InsertableMember> insertableMembers) {
        this.insertableMembers = insertableMembers;
    }

    public List<UpdatableMember> getUpdatableMembers() {
        return updatableMembers;
    }

    public void setUpdatableMembers(List<UpdatableMember> updatableMembers) {
        this.updatableMembers = updatableMembers;
    }

    public List<ReadOnlyMember> getReadOnlyMembers() {
        return readOnlyMembers;
    }

    public void setReadOnlyMembers(List<ReadOnlyMember> readOnlyMembers) {
        this.readOnlyMembers = readOnlyMembers;
    }
}
