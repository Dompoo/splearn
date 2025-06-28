package dompoo.splearn.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import static org.springframework.util.Assert.state;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Accessors(fluent = true)
@ToString
public class Member {

  private Long id;
  private Email email;
  private String nickname;
  private Password password;
  private MemberStatus status;

  private Member(Email email, String nickname, Password password) {
    this.email = email;
    this.nickname = nickname;
    this.password = password;
    this.status = MemberStatus.PENDING;
  }

  public static Member create(String emailValue, String nickname, String rawPassword) {
    Email email = new Email(emailValue);
    Password password = Password.encode(rawPassword);

    return new Member(email, nickname, password);
  }

  public void activate() {
    state(status == MemberStatus.PENDING, "PENDING 상태가 아닙니다.");

    status = MemberStatus.ACTIVE;
  }

  public void deactivate() {
    state(status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다.");

    status = MemberStatus.DEACTIVATED;
  }

  public boolean isPasswordCorrect(String rawPassword) {
    return password.matches(rawPassword);
  }

  public void changeNickname(String newNickname) {
    this.nickname = newNickname;
  }

  public void changePassword(String newRawPassword) {
    this.password = Password.encode(newRawPassword);
  }
}
