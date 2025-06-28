package dompoo.splearn.application;

import dompoo.splearn.application.provided.MemberFinder;
import dompoo.splearn.application.required.MemberRepository;
import dompoo.splearn.domain.member.Member;
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
  public Member find(Long memberId) {
    return memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. id: " + memberId));
  }
}
