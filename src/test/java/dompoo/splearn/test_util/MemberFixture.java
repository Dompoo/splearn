package dompoo.splearn.test_util;

import dompoo.splearn.domain.Member;
import org.springframework.test.util.ReflectionTestUtils;

public class MemberFixture {

  public static Member create(long id, String email, String nickname, String rawPassword) {
    Member member = Member.create(email, nickname, rawPassword);
    ReflectionTestUtils.setField(member, "id", id);
    return member;
  }

  public static Member createAny() {
    return Member.create("dompoo@email.com", "dompoo", "secret");
  }
}
