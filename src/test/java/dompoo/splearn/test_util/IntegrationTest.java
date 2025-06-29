package dompoo.splearn.test_util;

import dompoo.splearn.application.member.required.EmailSender;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Import(IntegrationTest.DummyEmailSenderConfig.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public abstract class IntegrationTest {

  @PersistenceContext
  protected EntityManager em;

  @TestConfiguration
  public static class DummyEmailSenderConfig {

    @Bean
    public EmailSender emailSender() {
      return (email, subject, body) -> {
        // 아무것도 하지 않는다.
      };
    }
  }
}
