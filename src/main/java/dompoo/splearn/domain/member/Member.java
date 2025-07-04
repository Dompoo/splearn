package dompoo.splearn.domain.member;

import dompoo.splearn.domain.share.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Objects;

import static org.springframework.util.Assert.state;

@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Getter
@Accessors(fluent = true)
@ToString
public class Member {

  private final Id id;
  private Email email;
  private String nickname;
  private Password password;
  private MemberStatus status;
  private MemberDetail detail;

  private Member(Email email, String nickname, Password password, MemberDetail memberDetail) {
    this.id = Id.generate("test", "member");
    this.email = email;
    this.nickname = nickname;
    this.password = password;
    this.status = MemberStatus.PENDING;
    this.detail = memberDetail;
  }

  public static Member create(String emailAddress, String nickname, String rawPassword, String profileAddress, String introduction) {
    Email email = new Email(emailAddress);
    Password password = Password.encode(rawPassword);
    MemberDetail detail = new MemberDetail(profileAddress, introduction);

    return new Member(email, nickname, password, detail);
  }

  public void activate() {
    state(status == MemberStatus.PENDING, "PENDING 상태가 아닙니다.");

    status = MemberStatus.ACTIVE;
    detail.initActivatedTimeToNow();
  }

  public void deactivate() {
    state(status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다.");

    status = MemberStatus.DEACTIVATED;
    detail.initDeactivatedTimeToNow();
  }

  public boolean isPasswordCorrect(String rawPassword) {
    return password.matches(rawPassword);
  }

  public void changeNickname(String newNickname) {
    this.nickname = newNickname;
  }

  public void changeDetail(String profileAddress, String introduction) {
    state(status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다.");

    detail.changeProfileAddressAndIntroduction(profileAddress, introduction);
  }

  public void changePassword(String newRawPassword) {
    this.password = Password.encode(newRawPassword);
  }

  public String emailAddress() {
    return this.email.address();
  }

  public String profileAddress() {
    return this.detail.profile().address();
  }

  public String introduction() {
    return this.detail.introduction();
  }

  public String idValue() {
    return this.id.value();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (!(obj instanceof Member other)) return false;

    return id != null && Objects.equals(id, other.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
