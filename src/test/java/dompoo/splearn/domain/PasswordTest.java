package dompoo.splearn.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PasswordTest {

  @Test
  void 패스워드를_생성하면_해시를_가지고_있는다() {
    var password = Password.encode("secret");

    assertThat(password.hashValue()).isNotEqualTo("secret");
  }

  @Test
  void 패스워드를_비교한다() {
    var password = Password.encode("secret");

    var result1 = password.matches("secret");
    var result2 = password.matches("dompoo");

    assertThat(result1).isTrue();
    assertThat(result2).isFalse();
  }
}
