package com.aleks.auth.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  private UUID id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Enumerated(EnumType.STRING)
  @ElementCollection(fetch = FetchType.EAGER)

  @CollectionTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id")
  )

  @Column(name = "role")
  private List<Role> roles;

  @Column(nullable = false)
  private boolean enabled;

  @Column(nullable = false)
  private Instant createdAt;

  @PrePersist
  public void prePersist() {

    createdAt = Instant.now();
  }
}