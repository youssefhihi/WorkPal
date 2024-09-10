package com.workPal.repositories.inteface;
import com.workPal.model.Member;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {

    void save(Member member);
    Optional<Member> findMember(UUID id);
    Map<UUID, Member> getAll();
    void update(Member member);
    void delete(String email);
}
