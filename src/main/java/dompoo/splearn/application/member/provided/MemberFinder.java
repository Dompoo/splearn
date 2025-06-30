package dompoo.splearn.application.member.provided;

import dompoo.splearn.domain.member.Member;
import jakarta.validation.constraints.NotNull;

public interface MemberFinder {

  Member find(@NotNull String memberId);
}
