package dompoo.splearn.test_util;

import dompoo.splearn.domain.PasswordEncoder;

public class FakePasswordEncoder implements PasswordEncoder {

  @Override
  public String encode(String rawPassword) {
    return rawPassword.toUpperCase();
  }

  @Override
  public boolean matches(String rawPassword, String passwordHash) {
    return encode(rawPassword).equals(passwordHash);
  }
}
