package com.example.aop.service;

import org.springframework.stereotype.Service;

/*
1. IoC 작업을 해야 함. 
이걸 객체로 만들어 달라 해야 함. 
컴포넌트를 붙인다. 
*/
@Service // 컴포넌트 애너테이션을 가지고 있는 애노테이션 , 
public class OrderService {

  // 실제 업무 처리 ( 비즈니스 메서드 ), Point Cut 대상이 될 타겟 메서드
  public String createOrder(String itemId){
    System.out.println("[주문 생성 메서드 시작], 주문 아이템 :"+itemId);
    // 주문시 완료까지 시간이 걸림. 1초 정도만 시간 해볼 예정. 
    try {
      Thread.sleep(1000); // 1초 지연이 되는 것 (자바 코드 멈추는 것)
    } catch (Exception e) {
      Thread.currentThread().interrupt();
    }
        System.out.println("[주문 생성 메서드 종료]");
    return "Order-"+itemId;
  }

}
