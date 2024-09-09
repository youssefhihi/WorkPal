package com.workPal.services.interfaces;

import com.workPal.model.Member;

import java.util.Map;

public interface MemberService {

    void addMember(Member member);

    void updateMember(Member member);

    void deleteMember(String email);

    Member getMemberByEmail(String email);

    Map<Integer, Member> getAllMembers();
}