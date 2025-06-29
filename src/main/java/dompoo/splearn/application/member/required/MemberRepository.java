package dompoo.splearn.application.member.required;

import dompoo.splearn.domain.member.Member;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MemberRepository extends Repository<Member, Long> {

  Member save(Member member);

  Optional<Member> findById(Long memberId);

  boolean existsByEmailAddress(String emailAddress);

  boolean existsByDetailProfileAddress(String profileAddress);

  Optional<Member> findByDetailProfileAddress(String profileAddress);
}
