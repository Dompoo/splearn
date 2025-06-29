package dompoo.splearn.adapter.webapi;

import dompoo.splearn.adapter.webapi.dto.MemberRegisterRequest;
import dompoo.splearn.adapter.webapi.dto.MemberRegisterResponse;
import dompoo.splearn.application.member.required.MemberRepository;
import dompoo.splearn.domain.member.Member;
import dompoo.splearn.domain.member.MemberStatus;
import dompoo.splearn.test_util.WebApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

class MemberApiTest extends WebApiTest {

  @Autowired
  MemberRepository memberRepository;

  @Test
  void 회원을_등록한다() throws Exception {
    var request = new MemberRegisterRequest(
        "dompoo@email.com",
        "dompoo",
        "secret",
        "profile",
        "introduction"
    );

    var response = mvc.post()
        .uri("/api/members")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request))
        .exchange();

    assertThat(response)
        .apply(print())
        .hasStatus(200)
        .hasContentType(MediaType.APPLICATION_JSON)
        .bodyJson()
        .convertTo(MemberRegisterResponse.class)
        .satisfies(resp -> {
          assertThat(resp.memberId()).isNotNull();
          assertThat(resp.emailAddress()).isEqualTo("dompoo@email.com");
          Optional<Member> findMember = memberRepository.findById(resp.memberId());
          assertThat(findMember).isPresent();
          assertThat(findMember.get().status()).isEqualTo(MemberStatus.PENDING);
        });
  }

  @Test
  void 중복된_이메일로_등록한다() throws Exception {
    insertMemberOf("dompoo@email.com");

    var request = new MemberRegisterRequest(
        "dompoo@email.com",
        "dompoo",
        "secret",
        "profile",
        "introduction"
    );

    var response = mvc.post()
        .uri("/api/members")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request))
        .exchange();

    assertThat(response)
        .apply(print())
        .hasStatus(409)
        .hasContentType(MediaType.APPLICATION_PROBLEM_JSON)
        .bodyJson()
        .convertTo(ProblemDetail.class)
        .satisfies(resp -> {
          assertThat(resp.getStatus()).isEqualTo(409);
          assertThat(resp.getDetail()).isEqualTo("중복된 이메일 주소입니다.");
        });
  }
}
