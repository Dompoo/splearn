package dompoo.splearn.domain.member;

public class DuplicatedEmailException extends RuntimeException {

  public DuplicatedEmailException(String message) {
    super(message);
  }
}
