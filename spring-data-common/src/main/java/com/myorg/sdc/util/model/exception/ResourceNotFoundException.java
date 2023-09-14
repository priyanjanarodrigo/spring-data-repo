package com.myorg.sdc.util.model.exception;

import lombok.ToString;

/**
 * ResourceNotFoundException a custom exception class that extends RuntimeException and is supposed
 * to be thrown when a requested resource is not found.
 */
@ToString
public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String message) {
    super(message);
  }
}

