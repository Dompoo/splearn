package dompoo.splearn.application.required;

import dompoo.splearn.test_util.MemberFixture;
import dompoo.splearn.test_util.PersistenceTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.*;

class MemberRepositoryTest extends PersistenceTest {

  @Autowired
  MemberRepository memberRepository;

  @Nested
  class 저장_테스트 {

    @Test
    void 멤버를_저장한다() {
      var member = MemberFixture.createAny();

      memberRepository.save(member);

      assertThat(member.id()).isNotNull();
      assertThatCode(() -> em.flush())
          .doesNotThrowAnyException();
    }

    @Test
    void 멤버를_저장할_때_이메일이_중복되면_예외가_발생한다() {
      var member1 = MemberFixture.createAny();
      var member2 = MemberFixture.createAny();

      memberRepository.save(member1);

      assertThatThrownBy(() -> memberRepository.save(member2))
          .isInstanceOf(DataIntegrityViolationException.class);
    }
  }
}
