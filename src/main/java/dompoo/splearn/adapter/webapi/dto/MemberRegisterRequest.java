package dompoo.splearn.adapter.webapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record MemberRegisterRequest(
    @NotBlank @Email String email,
    @NotBlank String nickname,
    @NotBlank String rawPassword,
    @NotBlank String profileAddress,
    @NotBlank String introduction
) {
}
