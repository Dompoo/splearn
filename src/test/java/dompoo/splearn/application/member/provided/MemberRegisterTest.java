package dompoo.splearn.application.member.provided;

import dompoo.splearn.domain.member.DuplicatedEmailException;
import dompoo.splearn.domain.member.DuplicatedProfileException;
import dompoo.splearn.domain.member.MemberStatus;
import dompoo.splearn.test_util.IntegrationTest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

class MemberRegisterTest extends IntegrationTest {

  @Autowired
  MemberRegister memberRegister;

  @Test
  void 멤버를_등록한다() {
    var savedMember = memberRegister.register("dompoo@email.com", "dompoo", "secret", "profile", "introduction");

    assertThat(savedMember.id()).isNotNull();
    assertThat(savedMember.status()).isEqualTo(MemberStatus.PENDING);
  }

  @Test
  void 존재하는_이메일로_멤버를_등록하면_예외가_발생한다() {
    memberRegister.register("same@email.com", "dompoo", "secret", "profile", "introduction");

    assertThatThrownBy(() -> memberRegister.register("same@email.com", "dompoo", "secret", "profile", "introduction"))
        .isInstanceOf(DuplicatedEmailException.class);
  }

  @Test
  void 닉네임이_비어있으면_예외가_발생한다() {
    assertThatThrownBy(() -> memberRegister.register("dompoo@email.com", "  ", "secret", "profile", "introduction"))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  void 멤버를_activate_한다() {
    var member = memberRegister.register("dompoo@email.com", "dompoo", "secret", "profile", "introduction");
    em.flush();
    em.clear();

    var activatedMember = memberRegister.activate(member.idValue());

    assertThat(activatedMember.status()).isEqualTo(MemberStatus.ACTIVE);
  }

  @Nested
  class 상세_정보_수정_테스트 {

    @Test
    void 멤버의_상세_정보를_수정한다() {
      var member = memberRegister.register("dompoo@email.com", "dompoo", "secret", "profile", "introduction");
      memberRegister.activate(member.idValue());
      em.flush();
      em.clear();

      var changedMember = memberRegister.changeDetail(member.idValue(), "newProfile", "newIntroduction");

      assertThat(changedMember.profileAddress()).isEqualTo("newProfile");
      assertThat(changedMember.introduction()).isEqualTo("newIntroduction");
    }

    @Test
    void 수정_정보가_지정되지_않으면_변경되지_않는다() {
      var member = memberRegister.register("dompoo@email.com", "dompoo", "secret", "profile", "introduction");
      memberRegister.activate(member.idValue());
      em.flush();
      em.clear();

      var changedMember = memberRegister.changeDetail(member.idValue(), null, "  ");

      assertThat(changedMember.profileAddress()).isEqualTo("profile");
      assertThat(changedMember.introduction()).isEqualTo("introduction");
    }

    @Test
    void 이미_등록된_프로필_주소이면_예외가_발생한다() {
      var member = memberRegister.register("dompoo@email.com", "dompoo", "secret", "profile", "introduction");
      memberRegister.register("song@email.com", "song", "secret", "songProfile", "introduction");
      memberRegister.activate(member.idValue());
      em.flush();
      em.clear();

      assertThatThrownBy(() -> memberRegister.changeDetail(member.idValue(), "songProfile", ""))
          .isInstanceOf(DuplicatedProfileException.class);
    }
  }
}
