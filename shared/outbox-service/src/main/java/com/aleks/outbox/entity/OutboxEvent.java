package com.aleks.outbox.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "outbox_events")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutboxEvent {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String aggregateType;

  private String aggregateId;

  private String topic;

  private String eventType;

  @Column(columnDefinition = "TEXT")
  private String payload;

  @Enumerated(EnumType.STRING)
  private OutboxStatus status;

  private Instant createdAt;

  @PrePersist
  void prePersist() {
    createdAt = Instant.now();
  }
}