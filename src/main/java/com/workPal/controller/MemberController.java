package com.workPal.controller;

import com.workPal.connectDB.DatabaseConnection;
import com.workPal.model.Manager;
import com.workPal.model.Member;
import com.workPal.services.interfaces.MemberService;
import com.workPal.services.serviceImpl.MemberServiceImpl;

import java.sql.Connection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class MemberController {
    private static final Connection connection = DatabaseConnection.connect();
    private final MemberService memberService = new MemberServiceImpl(connection);
    
    public void addMember(Member member) {
        memberService.addMember(member);
    }

    public void updateMember(Member member) {
        memberService.updateMember(member);
    }

    public void deleteMember(UUID id) {
        memberService.deleteMember(id);
    }

    public Optional<Member> getMemberById(UUID id) {
        return memberService.getMemberById(id);
    }

    public Map<UUID, Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    public  Map<UUID, Member> searchMembers(String query){return memberService.searchMembers(query);}

    public Optional<Member> getMemberByEmail(String email) {
        return memberService.getMemberByEmail(email);
    }
}