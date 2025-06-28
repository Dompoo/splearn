package dompoo.splearn.application.provided;

import dompoo.splearn.domain.Member;

public interface MemberFinder {
  
  Member find(Long memberId);
}
