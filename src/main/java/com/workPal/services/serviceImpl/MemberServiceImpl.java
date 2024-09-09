package com.workPal.services.serviceImpl;

import com.workPal.model.Member;
import com.workPal.repositories.inteface.MemberRepository;
import com.workPal.repositories.repositoryImpl.MemberImpl;
import com.workPal.services.interfaces.MemberService;

import java.sql.Connection;
import java.util.Map;

public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    public MemberServiceImpl(Connection connection) {
        this.memberRepository = new MemberImpl(connection);
    }

    @Override
    public void addMember(Member member) {
        try {
            memberRepository.save(member);
        } catch (Exception e) {
            System.out.println("Error adding member: " + e.getMessage());
        }
    }

    @Override
    public void updateMember(Member member) {
        try {
            memberRepository.update(member);
        } catch (Exception e) {
            System.out.println("Error updating member: " + e.getMessage());
        }
    }

    @Override
    public void deleteMember(String email) {
        try {
            memberRepository.delete(email);
        } catch (Exception e) {
            System.out.println("Error deleting member: " + e.getMessage());
        }
    }

    @Override
    public Member getMemberByEmail(String email) {
        return memberRepository.findMember(email);
    }

    @Override
    public Map<Integer, Member> getAllMembers() {
        return memberRepository.getAll();
    }
}