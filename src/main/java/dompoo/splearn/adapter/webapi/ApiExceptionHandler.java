package dompoo.splearn.adapter.webapi;

import dompoo.splearn.domain.member.DuplicatedEmailException;
import dompoo.splearn.domain.member.DuplicatedProfileException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({DuplicatedEmailException.class, DuplicatedProfileException.class})
  public ProblemDetail handleDuplicatedException(Exception e) {
    return buildDetail(HttpStatus.CONFLICT, e);
  }

  @ExceptionHandler(Exception.class)
  public ProblemDetail handleException(Exception e) {
    return buildDetail(HttpStatus.INTERNAL_SERVER_ERROR, e);
  }

  private static ProblemDetail buildDetail(HttpStatus status, Exception e) {
    ProblemDetail detail = ProblemDetail.forStatusAndDetail(status, e.getMessage());
    detail.setProperty("exception", e.getClass().getSimpleName());
    detail.setProperty("timestamp", LocalDateTime.now().toString());
    return detail;
  }
}
