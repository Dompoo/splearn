package dompoo.splearn.domain;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import static org.springframework.util.Assert.state;

@Getter
@Accessors(fluent = true)
@ToString
public class Member {

  private final Email email;
  private String nickname;
  private String passwordHash;
  private MemberStatus status;

  private Member(Email email, String nickname, String passwordHash) {
    this.email = email;
    this.nickname = nickname;
    this.passwordHash = passwordHash;
    this.status = MemberStatus.PENDING;
  }

  public static Member create(String emailValue, String nickname, String rawPassword, PasswordEncoder passwordEncoder) {
    String passwordHash = passwordEncoder.encode(rawPassword);
    Email email = new Email(emailValue);

    return new Member(email, nickname, passwordHash);
  }

  public void activate() {
    state(status == MemberStatus.PENDING, "PENDING 상태가 아닙니다.");

    status = MemberStatus.ACTIVE;
  }

  public void deactivate() {
    state(status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다.");

    status = MemberStatus.DEACTIVATED;
  }

  public boolean verifyPassword(String rawPassword, PasswordEncoder passwordEncoder) {
    return passwordEncoder.matches(rawPassword, passwordHash);
  }

  public void changeNickname(String newNickname) {
    this.nickname = newNickname;
  }

  public void changePassword(String newRawPassword, PasswordEncoder passwordEncoder) {
    this.passwordHash = passwordEncoder.encode(newRawPassword);
  }
}
