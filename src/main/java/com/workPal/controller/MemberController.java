package com.workPal.controller;

import com.workPal.model.Member;
import com.workPal.services.interfaces.MemberService;

import java.util.Map;

public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    public void addMember(Member member) {
        memberService.addMember(member);
    }

    public void updateMember(Member member) {
        memberService.updateMember(member);
    }

    public void deleteMember(String email) {
        memberService.deleteMember(email);
    }

    public Member getMemberByEmail(String email) {
        return memberService.getMemberByEmail(email);
    }

    public Map<Integer, Member> getAllMembers() {
        return memberService.getAllMembers();
    }
}