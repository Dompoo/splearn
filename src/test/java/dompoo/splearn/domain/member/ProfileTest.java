package dompoo.splearn.domain.member;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ProfileTest {

  @ParameterizedTest
  @ValueSource(strings = {"!!", "abc*123", "프로필"})
  void 프로필_생성시_기호가_들어가면_에외가_발생한다(String invalidProfileAddress) {
    assertThatThrownBy(() -> new Profile(invalidProfileAddress))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void 프로필_생성시_비어있으면_예외가_발생한다() {
    assertThatThrownBy(() -> new Profile(""))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void 프로필_생성시_너무_길면_예외가_발생한다() {
    assertThatThrownBy(() -> new Profile("1234567890123456"))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
