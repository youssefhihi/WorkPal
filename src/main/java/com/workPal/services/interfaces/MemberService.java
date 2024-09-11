package com.workPal.services.interfaces;

import com.workPal.model.Manager;
import com.workPal.model.Member;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface MemberService {

    void addMember(Member member);

    void updateMember(Member member);

    void deleteMember(UUID id);

    Optional<Member> getMemberById(UUID id);

    Map<UUID, Member> getAllMembers();

    Map<UUID, Member> searchMembers(String query);

    Optional<Member> getMemberByEmail(String email);

}