package dompoo.splearn.domain;

import dompoo.splearn.test_util.FakePasswordEncoder;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MemberTest {

  PasswordEncoder passwordEncoder;
  Member member;

  @BeforeEach
  void setUp() {
    this.passwordEncoder = new FakePasswordEncoder();
    this.member = Member.create("dompoo@email.com", "dompoo", "secret", passwordEncoder);
  }

  @Nested
  class 멤버_생성_테스트 {

    @Test
    void 멤버를_생성하면_가입_대기_상태이다() {
      assertThat(member.status()).isEqualTo(MemberStatus.PENDING);
    }
  }

  @Nested
  class 가입_승인_테스트 {

    @Test
    void 가입_승인한다() {
      member.activate();

      assertThat(member.status()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void 두번_가입_승인하면_예외가_발생한다() {
      member.activate();

      assertThatThrownBy(member::activate).isInstanceOf(IllegalStateException.class);
    }
  }

  @Nested
  class 탈퇴_테스트 {

    @Test
    void 탈퇴한다() {
      member.activate();

      member.deactivate();

      assertThat(member.status()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void 가입_승인하지_않고_탈퇴하면_예외가_발생한다() {
      assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 두번_탈퇴하면_예외가_발생한다() {
      member.activate();
      member.deactivate();

      assertThatThrownBy(member::deactivate).isInstanceOf(IllegalStateException.class);
    }
  }

  @Test
  void 비밀번호를_검증한다() {
    assertThat(member.verifyPassword("secret", passwordEncoder)).isTrue();
    assertThat(member.verifyPassword("incorrect", passwordEncoder)).isFalse();
  }

  @Test
  void 닉네임을_변경한다() {
    assertThat(member.nickname()).isEqualTo("dompoo");

    member.changeNickname("song");

    assertThat(member.nickname()).isEqualTo("song");
  }

  @Test
  void 비밀번호를_변경한다() {
    member.changePassword("verysecret", passwordEncoder);

    assertThat(member.verifyPassword("verysecret", passwordEncoder)).isTrue();
  }
}
