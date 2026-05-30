package com.aleks.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailNotificationService {

  public void sendEmail(
      String recipient,
      String subject,
      String body
  ) {

    log.info(
        """
        EMAIL SENT
        
        To: {}
        Subject: {}
        Message: {}
        """,
        recipient,
        subject,
        body
    );
  }
}