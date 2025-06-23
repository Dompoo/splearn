package dompoo.splearn.application.provided;

import dompoo.splearn.domain.Member;

public interface MemberRegister {
  
  Member register(String email, String nickname, String rawPassword);
}
