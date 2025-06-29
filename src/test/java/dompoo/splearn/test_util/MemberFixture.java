package dompoo.splearn.test_util;

import dompoo.splearn.domain.member.Member;
import org.springframework.test.util.ReflectionTestUtils;

public class MemberFixture {

  public static Member createAny() {
    return Member.create("dompoo@email.com", "dompoo", "secret", "profile", "introduction");
  }

  public static Member create(Long memberId, String emailAddress) {
    Member member = Member.create("dompoo@email.com", "dompoo", "secret", "profile", "introduction");
    ReflectionTestUtils.setField(member, "id", memberId);
    return member;
  }
}
