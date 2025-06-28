package dompoo.splearn.application;

import dompoo.splearn.application.provided.MemberFinder;
import dompoo.splearn.application.provided.MemberRegister;
import dompoo.splearn.application.required.EmailSender;
import dompoo.splearn.application.required.MemberRepository;
import dompoo.splearn.domain.DuplicatedEmailException;
import dompoo.splearn.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class MemberService implements MemberRegister, MemberFinder {

  private final MemberRepository memberRepository;
  private final EmailSender emailSender;

  @Override
  @Transactional
  public Member register(String email, String nickname, String rawPassword) {
    validateEmailNotDuplicated(email);
    Member member = Member.create(email, nickname, rawPassword);
    Member savedMember = memberRepository.save(member);
    sendWelcomeEmail(member);
    return savedMember;
  }

  @Override
  @Transactional
  public Member activate(Long memberId) {
    Member member = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. id: " + memberId));
    member.activate();
    return memberRepository.save(member);
  }

  private void validateEmailNotDuplicated(String email) {
    if (memberRepository.existsByEmail_Address(email)) {
      throw new DuplicatedEmailException("중복된 이메일입니다.");
    }
  }

  private void sendWelcomeEmail(Member member) {
    emailSender.send(member.email(), "등록을 완료해주세요.", "아래 링크를 클릭해서 등록을 완료해주세요.");
  }

  @Override
  public Member find(Long memberId) {
    return memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다. id: " + memberId));
  }
}
