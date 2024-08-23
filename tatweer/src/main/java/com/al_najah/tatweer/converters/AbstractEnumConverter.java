package com.al_najah.tatweer.converters;

import jakarta.persistence.AttributeConverter;

public abstract class AbstractEnumConverter<T extends Enum<T>>
    implements AttributeConverter<T, String> {
  private final Class<T> enumClass;

  protected AbstractEnumConverter(Class<T> enumClass) {
    this.enumClass = enumClass;
  }

  @Override
  public String convertToDatabaseColumn(T attribute) {
    return attribute != null ? attribute.name() : null;
  }

  @Override
  public T convertToEntityAttribute(String dbData) {
    return dbData != null ? Enum.valueOf(enumClass, dbData) : null;
  }
}
