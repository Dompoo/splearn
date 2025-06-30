package dompoo.splearn.application.member.provided;

import dompoo.splearn.domain.member.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface MemberRegister {

  Member register(
      @NotBlank @Email String email,
      @NotBlank String nickname,
      @NotBlank String rawPassword,
      @NotBlank String profileAddress,
      @NotBlank String introduction
  );

  Member activate(@NotNull Long memberId);

  Member changeDetail(
      @NotNull Long memberId,
      String profileAddress,
      String introduction
  );
}
