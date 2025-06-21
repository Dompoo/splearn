package dompoo.splearn.domain;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import static org.springframework.util.Assert.state;

@Getter
@Accessors(fluent = true)
@ToString
public class Member {

  private final String email;
  private final String nickname;
  private final String passwordHash;
  private MemberStatus status;

  public Member(String email, String nickname, String passwordHash) {
    this.email = email;
    this.nickname = nickname;
    this.passwordHash = passwordHash;
    this.status = MemberStatus.PENDING;
  }

  public void activate() {
    state(status == MemberStatus.PENDING, "PENDING 상태가 아닙니다.");

    status = MemberStatus.ACTIVE;
  }

  public void deactivate() {
    state(status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다.");

    status = MemberStatus.DEACTIVATED;
  }
}
