package com.aleks.notification.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private UUID orderId;

  private String recipient;

  private String subject;

  @Column(length = 5000)
  private String message;

  @Enumerated(EnumType.STRING)
  private NotificationStatus status;

  private Instant createdAt;

  @PrePersist
  public void prePersist() {
    createdAt = Instant.now();
  }
}