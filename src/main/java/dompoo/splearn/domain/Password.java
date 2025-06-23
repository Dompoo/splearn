package dompoo.splearn.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record Password(
    String hashValue
) {
  public static Password encode(String rawValue) {
    return new Password(hash(rawValue));
  }

  public boolean matches(String rawValue) {
    return hash(rawValue).equals(hashValue);
  }

  private static String hash(String rawValue) {
    return rawValue.toUpperCase();
  }
}
