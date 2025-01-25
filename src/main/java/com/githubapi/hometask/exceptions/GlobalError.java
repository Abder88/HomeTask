package com.githubapi.hometask.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public enum GlobalError {
  RESOURCE_NOT_FOUND("error.not_found", "Resource not found");

  private final String key;
  private final String description;

  // Constructor
  GlobalError(String key, String description) {
    this.key = key;
    this.description = description;
  }


  public String getKey() {
    return key;
  }

  public String getDescription() {
    return description;
  }
}

