package com.workPal.repositories.inteface;
import com.workPal.model.Member;
import java.util.Map;

public interface MemberRepository {

    void save(Member member);
    Member findMember(String email);
    Map<Integer, Member> getAll();
    void update(Member member);
    void delete(String email);
}
