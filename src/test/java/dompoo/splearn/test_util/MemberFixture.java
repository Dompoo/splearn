package dompoo.splearn.test_util;

import dompoo.splearn.domain.member.Member;

public class MemberFixture {

  public static Member createAny() {
    return Member.create("dompoo@email.com", "dompoo", "secret", "profile", "introduction");
  }
}
