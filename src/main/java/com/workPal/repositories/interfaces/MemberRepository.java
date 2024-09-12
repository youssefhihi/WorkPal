package com.workPal.repositories.interfaces;
import com.workPal.model.Member;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository {

    void save(Member member);
    Optional<Member> findMember(UUID id);
    Optional<Member> findMember(String email);
    Map<UUID, Member> getAll();
    void update(Member member);
    void delete(UUID id);
    Map<UUID, Member> searchMembers(String query);


}
