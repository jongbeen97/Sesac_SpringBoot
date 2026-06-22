package com.example.aop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aop.service.OrderService;

// import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

// 요청과 응답을 하는 서블릿을 만든다
// Restcontroller : 일반 컨트롤러 메서드의 응답은 ? view이다. 화면에 결과를 보여줄 HTML 파일의 이름인데 이걸 사용하면 메서드의 응답이 데이터 그 자체이다. 
// Order 서비스 : 포인트 컷이 되어서 치고 들어온다는 것. 함께 실행이 되는 구조이다. 오더 서비스 실행하면 되는데 스프링 컨테이너로 부터 가져오는 것 DI 가 필요하다. 
@RestController
// @AllArgsConstructor
@RequiredArgsConstructor
public class OrderController {
  
  private final OrderService orderService; 
  /*  가져오기 위해서 생성자 주입이 필요함 
  ** 원래 이 서비스를 가져오기 위해서 컨트롤러에 생성자 주입을 해야 한다
  public OrderController(OrderServicee orderService){
    this.orderservice = orderservice }
   
이것을 롬복을 활용하여 애너테이션 사용을 하면 된다. 
   생성자 주입의 경우 매개변수로 스프링 컨테이너에 빈이 자동으로 주입이 된다. 원래는 Autowired 를 적어주어야 하는데 생성자가 하나밖에 없는 경우 , 하나의 생성자를 주입해야 하는 경우 안 적어도 적은 것 처럼 매개변수로 만들어진 빈이 주입이 된다. 
  ** 대체하는 애너테이션 : AllArgsConstructor : 모든 필드를 매개변수로 활용하는 것 
final : 연결 고리 바꾸지 않도록 final처리를 하는 것이 중요하다
   ** 이 처리를 할때 전용 애너테이션이 있음 final 처리를 위한 전용 애너테이션 : @RequiredArgsConstructor
final 만 찾아서 주입을 해주는 것 실무적으로 널리 사용이 되는 것 All Args Constructor 를 별로 사용하지 않는다. 
생성 시점에 OrderService 다른 서비스로 바뀌거나, 참사가 벌어질수도 있다. OrderService로 연결을 해야 하는데 안전한 코딩을 하는 것이다. 
   Final 필드랑 궁합을 맞추는 것이다. 

   *DI가 일어나는 장소 : 생성자 주입을 통해 일어나는 DI : RequirdeArgsConstructor : Final 필드 전용의 생성자 주입을 이용한 DI*
   *롬복 사용한다면 이 부분이 실무 표준이다. 
  */
 @GetMapping("/aop_test")
  public String aopTest(){
    System.out.println("OrderService 클래스:"+orderService.getClass());
    System.out.println("=================");
    String result =  orderService.createOrder("item-001");
    System.out.println("=================");
    return result; // Order-item-001
  }

}
