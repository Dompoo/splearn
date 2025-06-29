package dompoo.splearn.adapter.webapi;

import dompoo.splearn.adapter.webapi.dto.MemberRegisterRequest;
import dompoo.splearn.adapter.webapi.dto.MemberRegisterResponse;
import dompoo.splearn.application.member.provided.MemberRegister;
import dompoo.splearn.domain.member.Member;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApi {

  private final MemberRegister memberRegister;

  @PostMapping("/api/members")
  public ResponseEntity<MemberRegisterResponse> register(@RequestBody @Valid MemberRegisterRequest request) {
    Member registeredMember = memberRegister.register(request.email(), request.nickname(), request.rawPassword(), request.profileAddress(), request.introduction());
    MemberRegisterResponse response = MemberRegisterResponse.from(registeredMember);
    return ResponseEntity.ok(response);
  }
}
