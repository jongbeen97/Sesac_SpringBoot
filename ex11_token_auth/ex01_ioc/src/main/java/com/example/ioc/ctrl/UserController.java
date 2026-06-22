package com.example.ioc.ctrl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.ioc.dto.UserDTO;
import com.example.ioc.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller // 사용자 에게 나는 요청 응답을 받는 서블릿이야 ? 라는 것을 알려주는 것
public class UserController {

// 알림서비스 객체 : 두군데 사용 서비스에서 객체 불러오기

  // 실무에서 사용하는 DI 방식
  private final NotificationService notificationService;
  private final ObjectMapper objectMapper;
  // 필드 선언 혹은 필드 초기화 할때 선언을 해주어야 한다.

  public UserController(
    // NotificationService타입의 빈이 2개 이상 있으면 이름(smsNotificationService)로 구분 가능
    @Qualifier("SMSNotificationService") NotificationService notificationService, 
    ObjectMapper objectMapper) {
    this.notificationService = notificationService;
    this.objectMapper = objectMapper;
  }

  // 회원 가입시 알림서비스 제공
  @RequestMapping(value = "/join", method = RequestMethod.GET)
  public void createUser() {
    notificationService.sendNotification("반갑다");
  }

  // 회원정보 수정시 알림서비스 제공
  @RequestMapping(value = "/modify" , method = RequestMethod.POST)
  public void modifyUser() {
    notificationService.sendNotification("수정됨");
  }

  // ObjectMapper 동작 테스트
  @RequestMapping("/jsontest")
  public void jsontest() {
    try {
      // 1. Java 객체 -> JSON 문자열로 바꾸는 과정(직렬화,serialization)
      UserDTO dto = new UserDTO("홍길동", 30);
      String jsonString = objectMapper.writeValueAsString(dto);
      System.out.println("생성된 JSON : " + jsonString);

      // 2. JSON 문자열을 자바 객체 ( 역직렬화 )
      String inputJson = "{\"name\":\"김철수\",\"age\":40}";
      UserDTO resultDto = objectMapper.readValue(inputJson, UserDTO.class);
      System.out.println("생성된 DTO : " + resultDto);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("예외발생 사유 : " + e.getMessage());
    }
  }

}
