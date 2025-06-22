package dompoo.splearn.domain;

import lombok.Value;
import lombok.experimental.Accessors;

@Value
@Accessors(fluent = true)
public class Password {

  String hashValue;

  private Password(String rawValue) {
    this.hashValue = hash(rawValue);
  }

  public static Password encode(String rawValue) {
    return new Password(rawValue);
  }

  public boolean matches(String rawValue) {
    return hash(rawValue).equals(hashValue);
  }

  private String hash(String rawValue) {
    return rawValue.toUpperCase();
  }
}
