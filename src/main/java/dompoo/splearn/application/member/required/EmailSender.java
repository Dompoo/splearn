package dompoo.splearn.application.member.required;

import dompoo.splearn.domain.member.Email;

public interface EmailSender {

  void send(Email email, String subject, String body);
}
