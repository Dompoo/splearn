package dompoo.splearn.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MemberTest {

  @Nested
  class 멤버_생성_테스트 {

    @Test
    void 멤버를_생성한다() {
      var member = new Member("dompoo@email.com", "dompoo", "secret");

      assertThat(member).isNotNull();
      assertThat(member.status()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void 속성_중_하나가_null이면_예외가_발생한다() {
      assertThatThrownBy(() -> new Member(null, "dompoo", "secret"));
    }
  }

  @Nested
  class 가입_승인_테스트 {

    @Test
    void 가입_승인한다() {
      var member = new Member("dompoo@email.com", "dompoo", "secret");

      member.activate();

      assertThat(member.status()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void 두번_가입_승인하면_예외가_발생한다() {
      var member = new Member("dompoo@email.com", "dompoo", "secret");

      member.activate();

      assertThatThrownBy(member::activate).isInstanceOf(IllegalStateException.class);
    }
  }

  @Nested
  class 탈퇴_테스트 {

    @Test
    void 탈퇴한다() {
      var member = new Member("dompoo@email.com", "dompoo", "secret");
      member.activate();

      member.deactivate();

      assertThat(member.status()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void 가입_승인하지_않고_탈퇴하면_예외가_발생한다() {
      var member = new Member("dompoo@email.com", "dompoo", "secret");

      assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 두번_탈퇴하면_예외가_발생한다() {
      var member = new Member("dompoo@email.com", "dompoo", "secret");
      member.activate();
      member.deactivate();

      assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
    }
  }
}
