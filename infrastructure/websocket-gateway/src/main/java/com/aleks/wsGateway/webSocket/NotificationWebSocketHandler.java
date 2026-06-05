package com.aleks.wsGateway.webSocket;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class NotificationWebSocketHandler
    extends TextWebSocketHandler {

  private final Set<WebSocketSession> sessions =
      ConcurrentHashMap.newKeySet();

  @Override
  public void afterConnectionEstablished(
      WebSocketSession session
  ) {

    sessions.add(session);
  }

  @Override
  public void afterConnectionClosed(
      WebSocketSession session,
      CloseStatus status
  ) {

    sessions.remove(session);
  }

  public void broadcast(
      String message
  ) {

    sessions.forEach(session -> {

      try {

        session.sendMessage(
            new TextMessage(message)
        );

      } catch (Exception ignored) {
      }
    });
  }
}