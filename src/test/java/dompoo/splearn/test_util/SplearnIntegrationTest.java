package dompoo.splearn.test_util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Import(SplearnTestConfig.class)
public abstract class SplearnIntegrationTest {

  @PersistenceContext
  protected EntityManager em;
}
