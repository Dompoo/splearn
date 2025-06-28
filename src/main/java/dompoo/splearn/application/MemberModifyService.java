package dompoo.splearn.application;

import dompoo.splearn.application.provided.MemberFinder;
import dompoo.splearn.application.provided.MemberRegister;
import dompoo.splearn.application.required.EmailSender;
import dompoo.splearn.application.required.MemberRepository;
import dompoo.splearn.domain.member.DuplicatedEmailException;
import dompoo.splearn.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class MemberModifyService implements MemberRegister {

  private final MemberFinder memberFinder;
  private final MemberRepository memberRepository;
  private final EmailSender emailSender;

  @Override
  @Transactional
  public Member register(String email, String nickname, String rawPassword, String profile, String introduction) {
    validateEmailNotDuplicated(email);
    Member member = Member.create(email, nickname, rawPassword, profile, introduction);
    Member savedMember = memberRepository.save(member);
    sendWelcomeEmail(member);
    return savedMember;
  }

  @Override
  @Transactional
  public Member activate(Long memberId) {
    Member member = memberFinder.find(memberId);
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
}
