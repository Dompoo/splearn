package dompoo.splearn.test_util;

import dompoo.splearn.application.required.EmailSender;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class SplearnTestConfig {

  @Bean
  public EmailSender emailSender() {
    return (email, subject, body) -> {
      // 아무것도 하지 않는다.
    };
  }
}
