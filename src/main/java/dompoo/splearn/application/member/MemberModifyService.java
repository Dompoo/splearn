package dompoo.splearn.application.member;

import dompoo.splearn.application.member.provided.MemberFinder;
import dompoo.splearn.application.member.provided.MemberRegister;
import dompoo.splearn.application.member.required.EmailSender;
import dompoo.splearn.application.member.required.MemberRepository;
import dompoo.splearn.domain.member.DuplicatedEmailException;
import dompoo.splearn.domain.member.DuplicatedProfileException;
import dompoo.splearn.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
@RequiredArgsConstructor
public class MemberModifyService implements MemberRegister {

  private final MemberFinder memberFinder;
  private final MemberRepository memberRepository;
  private final EmailSender emailSender;

  @Override
  @Transactional
  public Member register(String email, String nickname, String rawPassword, String profileAddress, String introduction) {
    validateEmailNotDuplicated(email);
    validateProfileAddressNotDuplicatedExceptTarget(profileAddress);
    Member member = Member.create(email, nickname, rawPassword, profileAddress, introduction);
    Member savedMember = memberRepository.save(member);
    sendWelcomeEmail(member);
    return savedMember;
  }

  private void validateProfileAddressNotDuplicatedExceptTarget(String profileAddress) {
    if (memberRepository.existsByDetailProfileAddress(profileAddress)) {
      throw new DuplicatedProfileException("중복된 프로필 주소입니다.");
    }
  }

  private void validateEmailNotDuplicated(String email) {
    if (memberRepository.existsByEmailAddress(email)) {
      throw new DuplicatedEmailException("중복된 이메일 주소입니다.");
    }
  }

  private void sendWelcomeEmail(Member member) {
    emailSender.send(member.email(), "등록을 완료해주세요.", "아래 링크를 클릭해서 등록을 완료해주세요.");
  }

  @Override
  @Transactional
  public Member activate(String memberId) {
    Member member = memberFinder.find(memberId);
    member.activate();
    return memberRepository.save(member);
  }

  @Override
  @Transactional
  public Member changeDetail(String memberId, String profileAddress, String introduction) {
    Member member = memberFinder.find(memberId);
    validateProfileAddressNotDuplicatedExceptTarget(member, profileAddress);
    member.changeDetail(profileAddress, introduction);
    return memberRepository.save(member);
  }

  private void validateProfileAddressNotDuplicatedExceptTarget(Member target, String profileAddress) {
    Optional<Member> sameProfileMember = memberRepository.findByDetailProfileAddress(profileAddress);
    if (sameProfileMember.isPresent() && !sameProfileMember.get().equals(target)) {
      throw new DuplicatedProfileException("중복된 프로필 주소입니다.");
    }
  }
}
