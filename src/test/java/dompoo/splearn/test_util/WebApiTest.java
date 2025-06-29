package dompoo.splearn.test_util;

import com.fasterxml.jackson.databind.ObjectMapper;
import dompoo.splearn.application.member.required.MemberRepository;
import dompoo.splearn.domain.member.Member;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Import(DummyEmailSenderConfig.class)
@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public abstract class WebApiTest {

  @Autowired
  protected MockMvcTester mvc;
  @Autowired
  protected ObjectMapper objectMapper;

  @Autowired
  private MemberRepository memberRepository;

  protected void insertMemberOf(String emailAddress) {
    memberRepository.save(Member.create(emailAddress, "dompoo", "secret", "profile", "introduction"));
  }
}
