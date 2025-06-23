package dompoo.splearn.test_util;

import dompoo.splearn.domain.Member;

public class MemberFixture {

  public static Member createAny() {
    return Member.create("dompoo@email.com", "dompoo", "secret");
  }
}
