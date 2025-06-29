package dompoo.splearn.domain.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Objects;

import static org.springframework.util.Assert.state;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Accessors(fluent = true)
@ToString
public class MemberDetail {

  private Long id;
  private Profile profile;
  private String introduction;
  private LocalDateTime createdAt;
  private LocalDateTime activatedAt;
  private LocalDateTime deactivatedAt;

  MemberDetail(String profileAddress, String introduction) {
    this.profile = new Profile(profileAddress);
    this.introduction = introduction;
    this.createdAt = LocalDateTime.now();
  }

  void initActivatedTimeToNow() {
    state(activatedAt == null, "activatedAt은 null 이어야 합니다.");

    activatedAt = LocalDateTime.now();
  }

  void initDeactivatedTimeToNow() {
    state(deactivatedAt == null, "deactivatedAt은 null 이어야 합니다.");

    deactivatedAt = LocalDateTime.now();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (!(obj instanceof MemberDetail other)) return false;

    return id != null && Objects.equals(id, other.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
