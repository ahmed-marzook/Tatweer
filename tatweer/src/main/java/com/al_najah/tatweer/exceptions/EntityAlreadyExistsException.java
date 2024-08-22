package com.al_najah.tatweer.exceptions;

import lombok.Getter;

public class EntityAlreadyExistsException extends RuntimeException {

  public EntityAlreadyExistsException(String message) {
    super(message);
  }

}
