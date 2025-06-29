package dompoo.splearn.application.member.provided;

import dompoo.splearn.domain.member.Member;
import org.springframework.lang.NonNull;

public interface MemberFinder {

  Member find(@NonNull Long memberId);
}
