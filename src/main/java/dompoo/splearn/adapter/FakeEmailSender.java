package dompoo.splearn.adapter;

import dompoo.splearn.application.required.EmailSender;
import dompoo.splearn.domain.member.Email;
import org.springframework.context.annotation.Fallback;
import org.springframework.stereotype.Component;

@Fallback
@Component
public class FakeEmailSender implements EmailSender {

  @Override
  public void send(Email email, String subject, String body) {
    System.out.println("이메일 전송 email : " + email.address() + ", subject : " + subject + ", body : " + body);
  }
}
