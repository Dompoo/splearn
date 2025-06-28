package dompoo.splearn.application.provided;

import dompoo.splearn.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public interface MemberRegister {

  Member register(
      @NotBlank @Email String email,
      @NotBlank String nickname,
      @NotBlank String rawPassword
  );
}
