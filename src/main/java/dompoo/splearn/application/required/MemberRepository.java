package dompoo.splearn.application.required;

import dompoo.splearn.domain.Member;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface MemberRepository extends Repository<Member, Long> {

  Member save(Member member);

  Optional<Member> findById(Long memberId);

  boolean existsByEmail_Address(String emailAddress);
}
