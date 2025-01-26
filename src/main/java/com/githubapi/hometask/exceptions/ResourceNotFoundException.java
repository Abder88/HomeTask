package com.githubapi.hometask.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  final Integer internalCode;
  final HttpStatus status;
  final String key;
  final String debugMessage;
  final String[] params;


  public ResourceNotFoundException(@NonNull final Class<?> entity, final Object identifier) {
    this(entity.getSimpleName().toLowerCase(), identifier);
  }

  public ResourceNotFoundException(@NonNull final String resourceName, final Object identifier) {
    this(-1, HttpStatus.NOT_FOUND, GlobalError.RESOURCE_NOT_FOUND, resourceName.toLowerCase(),
        identifier.toString());
  }

  public ResourceNotFoundException(final Integer internalCode, final HttpStatus status,
      final GlobalError message, final String... args) {
    this.internalCode = internalCode;
    this.status = status;
    this.key = message.getKey();
    this.debugMessage = message.getDescription();
    this.params = args;
  }

}
