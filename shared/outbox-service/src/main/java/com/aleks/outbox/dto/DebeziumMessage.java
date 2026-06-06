package com.aleks.outbox.dto;

import lombok.Data;

@Data
public class DebeziumMessage {

  private Schema schema;

  private String payload;

  @Data
  public static class Schema {

    private String type;

    private boolean optional;
  }
}
