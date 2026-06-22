package com.example.ioc.service;

import org.springframework.stereotype.Component;

@Component
public class EmailNotificationService implements NotificationService{
  @Override
  public void sendNotification(String message) {
    // TODO Auto-generated method stub
    System.out.println("이메일 발송"+message);
  }
}
