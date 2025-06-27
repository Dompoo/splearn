package dompoo.splearn.application.provided;

import dompoo.splearn.application.MemberService;
import dompoo.splearn.application.required.EmailSender;
import dompoo.splearn.application.required.MemberRepository;
import dompoo.splearn.domain.Member;
import dompoo.splearn.domain.MemberStatus;
import dompoo.splearn.test_util.MemberFixture;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberRegisterTest {

  @Test
  void 멤버를_등록한다() {
    var memberRepository = mock(MemberRepository.class);
    var emailSender = mock(EmailSender.class);
    var sut = new MemberService(memberRepository, emailSender);
    Member member = MemberFixture.create(1, "dompoo@email.com", "dompoo", "secret");
    when(memberRepository.save(any(Member.class))).thenReturn(member);

    var savedMember = sut.register(member.email().address(), "dompoo", "secret");

    assertThat(savedMember.id()).isNotNull();
    assertThat(savedMember.status()).isEqualTo(MemberStatus.PENDING);
    verify(emailSender).send(eq(member.email()), anyString(), anyString());
  }
}
