package dompoo.splearn.domain;

public class DuplicatedEmailException extends RuntimeException {

  public DuplicatedEmailException(String message) {
    super(message);
  }
}
