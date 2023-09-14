package com.myorg.sdc.util.model.exception;

import lombok.ToString;

/**
 * InternalServerException a custom exception class that extends RuntimeException and is supposed to
 * be thrown when an internal server issue is occurred.
 */
@ToString
public class InternalServerException extends RuntimeException {

  public InternalServerException(String message) {
    super(message);
  }
}
