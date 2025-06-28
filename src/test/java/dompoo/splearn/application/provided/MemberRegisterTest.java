package dompoo.splearn.application.provided;

import dompoo.splearn.domain.DuplicatedEmailException;
import dompoo.splearn.domain.MemberStatus;
import dompoo.splearn.test_util.SplearnIntegrationTest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;

class MemberRegisterTest extends SplearnIntegrationTest {

  @Autowired
  MemberRegister sut;
  @PersistenceContext
  EntityManager em;

  @Test
  void 멤버를_등록한다() {
    var savedMember = sut.register("dompoo@email.com", "dompoo", "secret");

    assertThat(savedMember.id()).isNotNull();
    assertThat(savedMember.status()).isEqualTo(MemberStatus.PENDING);
  }

  @Test
  void 존재하는_이메일로_멤버를_등록하면_예외가_발생한다() {
    sut.register("same@email.com", "dompoo", "secret");

    assertThatThrownBy(() -> sut.register("same@email.com", "dompoo", "secret"))
        .isInstanceOf(DuplicatedEmailException.class);
  }

  @Test
  void 닉네임이_비어있으면_예외가_발생한다() {
    assertThatThrownBy(() -> sut.register("dompoo@email.com", "  ", "secret"))
        .isInstanceOf(ConstraintViolationException.class);
  }

  @Test
  void 멤버를_activate_한다() {
    var member = sut.register("dompoo@email.com", "dompoo", "secret");
    em.flush();
    em.clear();

    var activatedMember = sut.activate(member.id());

    assertThat(activatedMember.status()).isEqualTo(MemberStatus.ACTIVE);
  }
}
