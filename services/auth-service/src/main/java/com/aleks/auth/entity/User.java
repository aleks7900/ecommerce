package com.aleks.auth.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
  private Set<Role> roles;

  @Column(nullable = false)
  private boolean enabled;

  @Column(nullable = false)
  private Instant createdAt;

  private String firstName;

  private String lastName;

  private String avatarUrl;

  private Instant lastLogin;

  @PrePersist
  public void prePersist() {

    createdAt = Instant.now();
  }
}