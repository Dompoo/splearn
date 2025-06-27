package dompoo.splearn.application.required;

import dompoo.splearn.domain.Member;
import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<Member, Long> {

  Member save(Member member);
}
