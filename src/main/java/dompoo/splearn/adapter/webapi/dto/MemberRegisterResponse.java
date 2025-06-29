package dompoo.splearn.adapter.webapi.dto;

import dompoo.splearn.domain.member.Member;

public record MemberRegisterResponse(
    Long memberId,
    String emailAddress
) {
  public static MemberRegisterResponse from(Member member) {
    return new MemberRegisterResponse(member.id(), member.emailAddress());
  }
}
