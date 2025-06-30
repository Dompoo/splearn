package dompoo.splearn.application.member.required;

import dompoo.splearn.domain.member.Member;
import dompoo.splearn.domain.share.Id;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MemberRepository extends Repository<Member, Id> {

  Member save(Member member);

  Optional<Member> findById(Id memberId);

  boolean existsByEmailAddress(String emailAddress);

  boolean existsByDetailProfileAddress(String profileAddress);

  Optional<Member> findByDetailProfileAddress(String profileAddress);
}
