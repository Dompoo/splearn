package dompoo.splearn.domain.share;

import com.github.f4b6a3.tsid.TsidCreator;

public record Id(
    String value
) {
  private static final String VALUE_FORMAT = "%s_%s_%s";

  public static Id generate(String env, String entityName) {
    String snowflake = createSnowflake();
    return new Id(VALUE_FORMAT.formatted(env, entityName, snowflake));
  }

  private static String createSnowflake() {
    return TsidCreator.getTsid().toString();
  }
}
