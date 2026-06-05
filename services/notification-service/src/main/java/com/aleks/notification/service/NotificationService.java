package com.aleks.notification.service;

import com.aleks.notification.entity.Notification;
import com.aleks.notification.entity.NotificationStatus;
import com.aleks.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

  private final NotificationRepository repository;

  private final RedisTemplate redisTemplate;

  public Notification save(
      Notification notification
  ) {
    notification.setStatus(
        NotificationStatus.SENT
    );

    redisTemplate.opsForList()
        .leftPush(
            "notification:user:" + notification.getId(),
            notification
        );

    return repository.save(
        notification
    );
  }
}