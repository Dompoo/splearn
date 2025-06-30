package dompoo.splearn.domain.share;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class IdTest {

  @Test
  void 생성된_ID는_시간순으로_정렬된다() {
    List<Id> ids = new ArrayList<>();
    for (int i = 0; i < 10000; i++) {
      ids.add(Id.generate("test", "member"));
    }

    for (int i = 0; i < 9999; i++) {
      Id cur = ids.get(i);
      Id next = ids.get(i + 1);
      assertTrue(cur.value().compareTo(next.value()) < 0);
    }
  }

  @Test
  void 생성된_ID는_중복이_없다() {
    List<Id> ids = new ArrayList<>();
    for (int i = 0; i < 10000; i++) {
      ids.add(Id.generate("test", "member"));
    }

    Set<Id> set = new HashSet<>(ids);
    assertEquals(set.size(), ids.size());
  }

}
