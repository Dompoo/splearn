package dompoo.splearn.domain.member;

import dompoo.splearn.test_util.MemberFixture;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MemberTest {

  @Nested
  class 멤버_생성_테스트 {

    @Test
    void 멤버를_생성하면_가입_대기_상태이다() {
      var member = MemberFixture.createAny();

      assertThat(member.status()).isEqualTo(MemberStatus.PENDING);
    }

    @ParameterizedTest
    @ValueSource(strings = {"wrong@email", "wrong.com", "wrong"})
    void 올바르지_않은_이메일_형식이면_예외가_발생한다(String invalidEmailValue) {
      assertThatThrownBy(() -> Member.create(invalidEmailValue, "dompoo", "secret", "profile", "introduction"))
          .isInstanceOf(IllegalArgumentException.class);
    }
  }

  @Nested
  class 가입_승인_테스트 {

    @Test
    void 가입_승인한다() {
      var member = MemberFixture.createAny();

      member.activate();

      assertThat(member.status()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void 두번_가입_승인하면_예외가_발생한다() {
      var member = MemberFixture.createAny();

      member.activate();

      assertThatThrownBy(member::activate).isInstanceOf(IllegalStateException.class);
    }
  }

  @Nested
  class 탈퇴_테스트 {

    @Test
    void 탈퇴한다() {
      var member = MemberFixture.createAny();

      member.activate();

      member.deactivate();

      assertThat(member.status()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void 가입_승인하지_않고_탈퇴하면_예외가_발생한다() {
      var member = MemberFixture.createAny();

      assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 두번_탈퇴하면_예외가_발생한다() {
      var member = MemberFixture.createAny();

      member.activate();
      member.deactivate();

      assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
    }
  }

  @Test
  void 비밀번호를_검증한다() {
    var member = MemberFixture.createAny();

    assertThat(member.isPasswordCorrect("secret")).isTrue();
    assertThat(member.isPasswordCorrect("incorrect")).isFalse();
  }

  @Test
  void 닉네임을_변경한다() {
    var member = MemberFixture.createAny();

    assertThat(member.nickname()).isEqualTo("dompoo");

    member.changeNickname("song");

    assertThat(member.nickname()).isEqualTo("song");
  }

  @Test
  void 비밀번호를_변경한다() {
    var member = MemberFixture.createAny();

    member.changePassword("verysecret");

    assertThat(member.isPasswordCorrect("verysecret")).isTrue();
  }
}
