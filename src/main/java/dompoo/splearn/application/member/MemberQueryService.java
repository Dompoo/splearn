package dompoo.splearn.application.member;

import dompoo.splearn.application.member.provided.MemberFinder;
import dompoo.splearn.application.member.required.MemberRepository;
import dompoo.splearn.domain.member.Member;
import dompoo.splearn.domain.share.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class MemberQueryService implements MemberFinder {

  private final MemberRepository memberRepository;

  @Override
  @Transactional(readOnly = true)
  public Member find(String memberId) {
    return memberRepository.findById(new Id(memberId)).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. id: " + memberId));
  }
}
