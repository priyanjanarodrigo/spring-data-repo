package com.myorg.sdc.advice;

import static com.myorg.sdc.util.Constants.DOT;
import static com.myorg.sdc.util.Constants.INT_ONE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.myorg.sdc.util.model.dto.response.error.ApiErrorResponse;
import com.myorg.sdc.util.model.exception.InternalServerException;
import com.myorg.sdc.util.model.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ControllerAdvice class includes the exception handlers and for different exceptions and returns
 * the configured http status codes accordingly.
 */
@RestControllerAdvice
public class ControllerAdvice {

  /**
   * Exception handler for MethodArgumentNotValidException.
   *
   * @param methodArgumentNotValidException MethodArgumentNotValidException
   * @return Map<String, String>
   */
  @ResponseStatus(value = BAD_REQUEST)
  @ExceptionHandler({MethodArgumentNotValidException.class})
  public Map<String, String> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException methodArgumentNotValidException) {
    return methodArgumentNotValidException
        .getBindingResult()
        .getFieldErrors()
        .stream()
        .collect(Collectors.toMap(
            FieldError::getField,
            DefaultMessageSourceResolvable::getDefaultMessage,
            (firstKey, secondKey) -> secondKey,
            HashMap::new));
  }

  /**
   * Exception handler for ConstraintViolationException.
   *
   * @param constraintViolationException ConstraintViolationException
   * @return Map<String, String>
   */
  @ResponseStatus(BAD_REQUEST)
  @ExceptionHandler({ConstraintViolationException.class})
  public Map<String, String> handleConstraintViolationException(
      ConstraintViolationException constraintViolationException) {

    return constraintViolationException
        .getConstraintViolations()
        .stream()
        .collect(Collectors.toMap(constraintViolation -> {
          String propertyPath = constraintViolation.getPropertyPath().toString();
          return (propertyPath.contains(DOT))
              ? propertyPath.substring(propertyPath.lastIndexOf(DOT) + INT_ONE)
              : propertyPath;
        }, ConstraintViolation::getMessage));
  }

  /**
   * Exception handler for exceptions which are supposed indicate Http Status 404  - Not Found.
   *
   * @param exception Exception
   * @param request   Http Servlet Request
   * @return ResponseEntity<ApiErrorResponse>
   */
  @ExceptionHandler({ResourceNotFoundException.class})
  public ResponseEntity<ApiErrorResponse> handleNotFoundException(
      RuntimeException exception, HttpServletRequest request
  ) {
    return this.getResponseEntity(exception, request, NOT_FOUND);
  }

  /**
   * Exception handler for exceptions which are supposed to indicate Http Status 500  - Internal
   * Server Error.
   *
   * @param exception Exception
   * @param request   Http Servlet Request
   * @return ResponseEntity<ApiErrorResponse>
   */
  @ExceptionHandler({InternalServerException.class})
  public ResponseEntity<ApiErrorResponse> handleInternalServerErrorException(
      RuntimeException exception, HttpServletRequest request
  ) {
    return this.getResponseEntity(exception, request, INTERNAL_SERVER_ERROR);
  }

  /**
   * Creates and returns the ResponseEntity of type ApiErrorResponse based on the provided
   * exception, request and http status.
   *
   * @param exception  Exception
   * @param request    Http Servlet Request
   * @param httpStatus Http Status
   * @return ResponseEntity<ApiErrorResponse>
   */
  private ResponseEntity<ApiErrorResponse> getResponseEntity(
      RuntimeException exception, HttpServletRequest request, HttpStatus httpStatus) {
    ApiErrorResponse apiErrorResponse = new ApiErrorResponse(httpStatus.value(),
        request.getRequestURI(), exception.getMessage(), LocalDateTime.now());
    return new ResponseEntity<>(apiErrorResponse, httpStatus);
  }
}
