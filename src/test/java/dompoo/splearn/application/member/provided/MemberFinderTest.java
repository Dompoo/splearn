package dompoo.splearn.application.member.provided;

import dompoo.splearn.test_util.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

class MemberFinderTest extends IntegrationTest {

  @Autowired
  MemberFinder memberFinder;
  @Autowired
  MemberRegister memberRegister;

  @Test
  void 멤버를_조회한다() {
    var member = memberRegister.register("dompoo@email.com", "dompoo", "secret", "profile", "introduction");
    em.flush();
    em.clear();

    var findMember = memberFinder.find(member.idValue());

    assertThat(findMember.id()).isEqualTo(member.id());
  }

  @Test
  void 존재하지_않는_멤버를_조회하면_예외가_발생한다() {
    var member = memberRegister.register("dompoo@email.com", "dompoo", "secret", "profile", "introduction");
    em.flush();
    em.clear();

    assertThatThrownBy(() -> memberFinder.find("invalidMemberId"))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
