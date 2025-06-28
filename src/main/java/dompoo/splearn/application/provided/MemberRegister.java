package dompoo.splearn.application.provided;

import dompoo.splearn.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.NonNull;

public interface MemberRegister {

  Member register(
      @NotBlank @Email String email,
      @NotBlank String nickname,
      @NotBlank String rawPassword
  );

  Member activate(@NonNull Long memberId);
}
