package dompoo.learningtest.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import org.junit.jupiter.api.DisplayName;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "dompoo.learningtest.archunit")
public class ArchUnitLearningTest {

  @ArchTest
  @DisplayName("application 패키지에 의존하는 클래스는 application, adapter 패키지에만 존재해야 한다")
  void application(JavaClasses classes) {
    classes().that().resideInAnyPackage("..application..")
        .should().onlyHaveDependentClassesThat().resideInAnyPackage("..application..", "..adapter..")
        .check(classes);
  }

  @ArchTest
  @DisplayName("application 패키지의 클래스는 adapter 패키지의 클래스에 의존하면 안된다.")
  void application2(JavaClasses classes) {
    noClasses().that().resideInAnyPackage("..application..")
        .should().dependOnClassesThat().resideInAnyPackage("..adapter..")
        .check(classes);
  }

  @ArchTest
  @DisplayName("adapter 패키지에 의존하는 클래스는 adapter 패지키에만 존재해야 한다")
  void adapter(JavaClasses classes) {
    classes().that().resideInAPackage("..adapter..")
        .should().onlyHaveDependentClassesThat().resideInAnyPackage("..adapter..")
        .check(classes);
  }

  @ArchTest
  @DisplayName("domain 패키지의 클래스는 domain 패키지의 클래스에만 의존해야 한다.")
  void domain(JavaClasses classes) {
    classes().that().resideInAnyPackage("..domain..")
        .should().onlyDependOnClassesThat().resideInAnyPackage("..domain..", "..java..")
        .check(classes);
  }
}
