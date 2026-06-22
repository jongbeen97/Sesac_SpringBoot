package com.example.ioc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

/*
@Configuration : 스프링 컨테이너에 Bean 을 등록할 수 있는 Method를 포함하는 클래스 임을 선언 (메서드를 가지고 있다)
*/
@Configuration
public class AppConfig {

  /* Bean 으로 등록할 객체를 반환하는 메서드 */
  @Bean
  /* Gson라이브러리 - Jackson 라이브러리
  - 객체를 JSON데이터로 바꿔주고 역직렬화 를 해주는 것. 
  - 같은 용도로 내장이 된 것이 하나 있다 "SpringWeb"
    * Jackson : 직렬화,역직렬화를 수행해주는 라이브러리 
    * 클래스 명 : ObjectMapper , 이미 Referenced에 들어있다
  */
 ObjectMapper objectMapper(){ // Bean타입 " ObjectMapper " , Bean 이름 (objectMapper)
  return new ObjectMapper();
 }
 /* 
 - 객체를 JSON으로 반환해주는 일 - 개발을 할 때 쓸 가능성 높음 
 - 데이터를 실어나르는 객체 : ResultSet -> DTO로 전송 객체를 모아두고 전송함.
 - Class로 만드는데 나중에는 record로 만들 수 있다. 
 - 이름 나이 저장 할 예정. 
 */
}
