package com.dobot.PaymentSplit.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.log4j.Log4j2;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {
  @Value("${error.printStackTrace}")
  private boolean printStackTrace;

  // validation error 처리
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    log.error("Validation exception: ", e);

    List<ValidationError> validationErrors = e.getBindingResult().getFieldErrors().stream()
        .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage())).toList();
    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    String stackTrace = null;
    if (printStackTrace) {
      stackTrace = Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("\n"));
    }

    ErrorResponseDto errorResponseDto = new ErrorResponseDto(httpStatus.value(), "Validation error",
        LocalDateTime.now(), validationErrors, stackTrace);
    return new ResponseEntity<>(errorResponseDto, httpStatus);
  }

  // internal server error 처리
  public ResponseEntity<ErrorResponseDto> handleUncaughtException(Exception e) {
    log.error("Uncaught exception: ", e);
    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    ErrorResponseDto errorResponseDto = new ErrorResponseDto(httpStatus.value(),
        "Internal server error",
        LocalDateTime.now(), null, e.getStackTrace().toString());
    return new ResponseEntity<>(errorResponseDto, httpStatus);
  }

  private record ErrorResponseDto(int httpStatusCode, String message, LocalDateTime timestamp,
      List<ValidationError> validationErrors, String stackTrace) {

  }

  private record ValidationError(String field, String message) {

  }

}
