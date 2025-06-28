package dompoo.splearn.domain.member;

import java.util.regex.Pattern;

import static org.springframework.util.Assert.isTrue;

public record Email(
    String address
) {
  private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");

  public Email {
    isTrue(EMAIL_PATTERN.matcher(address).matches(), "올바르지 않은 이메일 형식입니다.");
  }
}
