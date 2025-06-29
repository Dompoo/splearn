package dompoo.splearn.adapter.webapi;

import dompoo.splearn.adapter.webapi.dto.MemberRegisterRequest;
import dompoo.splearn.adapter.webapi.dto.MemberRegisterResponse;
import dompoo.splearn.application.member.provided.MemberRegister;
import dompoo.splearn.test_util.MemberFixture;
import dompoo.splearn.test_util.WebApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class MemberApiTest extends WebApiTest {

  @MockitoBean
  MemberRegister memberRegister;

  @Test
  void 회원_등록_API_테스트() throws Exception {
    var request = new MemberRegisterRequest(
        "dompoo@email.com",
        "dompoo",
        "secret",
        "profile",
        "introduction"
    );
    var mockMember = MemberFixture.create(1L, "dompoo@email.com");
    when(memberRegister.register(anyString(), anyString(), anyString(), anyString(), anyString()))
        .thenReturn(mockMember);

    var response = mvc.post()
        .uri("/api/members")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request))
        .exchange();

    assertThat(response)
        .hasStatus(200)
        .hasContentType(MediaType.APPLICATION_JSON)
        .bodyJson()
        .convertTo(MemberRegisterResponse.class)
        .satisfies(resp -> {
          assertThat(resp.memberId()).isEqualTo(1L);
          assertThat(resp.emailAddress()).isEqualTo("dompoo@email.com");
        });
  }
}
