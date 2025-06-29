package dompoo.splearn.domain.member;

import java.util.regex.Pattern;

import static org.springframework.util.Assert.isTrue;

public record Profile(
    String address
) {
  private static final Pattern PROFILE_ADDRESS_PATTERN = Pattern.compile("^[a-zA-Z0-9]{1,15}$");

  public Profile {
    isTrue(PROFILE_ADDRESS_PATTERN.matcher(address).matches(), "올바르지 않은 프로필 주소 형식입니다.");
  }
}
