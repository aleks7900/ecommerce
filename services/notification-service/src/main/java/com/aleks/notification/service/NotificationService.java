package com.aleks.notification.service;

import com.aleks.notification.entity.Notification;
import com.aleks.notification.entity.NotificationStatus;
import com.aleks.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

  private final NotificationRepository repository;

  public Notification save(
      Notification notification
  ) {

    notification.setStatus(
        NotificationStatus.SENT
    );

    return repository.save(
        notification
    );
  }
}