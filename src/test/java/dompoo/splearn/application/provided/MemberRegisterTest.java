package dompoo.splearn.application.provided;

import dompoo.splearn.domain.DuplicatedEmailException;
import dompoo.splearn.domain.MemberStatus;
import dompoo.splearn.test_util.SplearnTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Import(SplearnTestConfig.class)
class MemberRegisterTest {

  @Autowired
  MemberRegister sut;

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
}
