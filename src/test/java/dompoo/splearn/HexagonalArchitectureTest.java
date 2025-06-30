package dompoo.splearn;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.library.Architectures;

@AnalyzeClasses(packages = "dompoo.splearn", importOptions = ImportOption.DoNotIncludeTests.class)
public class HexagonalArchitectureTest {

  private static final String DOMAIN = "domain";
  private static final String APPLICATION = "application";
  private static final String ADAPTER = "adapter";

  @ArchTest
  void hexagonalArchitectureTest(JavaClasses classes) {
    Architectures.layeredArchitecture().consideringOnlyDependenciesInLayers()
        .layer(DOMAIN).definedBy("dompoo.splearn.domain..")
        .layer(APPLICATION).definedBy("dompoo.splearn.application..")
        .layer(ADAPTER).definedBy("dompoo.splearn.adapter..")
        .whereLayer(DOMAIN).mayNotAccessAnyLayer()
        .whereLayer(APPLICATION).mayOnlyAccessLayers(DOMAIN)
        .whereLayer(ADAPTER).mayOnlyAccessLayers(APPLICATION, DOMAIN)
        .check(classes);
  }
}
