package com.example.request.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.example.request.dto.UserRequestDTO;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/api/users")
public class RequestController {

  // 테스트 요청 주소
  // http://localhost:8080/api/users/v1?name="홍길동"&age=30

  // 요청 파라미터 1. httpservletRequest 활용
  @GetMapping("/v1")
  public void legacy(HttpServletRequest request) {
    // 모든 요청 파라미터는 String 타입으로 전달
    String name = request.getParameter("name");
    String strAge = request.getParameter("age");

    int age = 0;
    if (strAge != null && !strAge.isBlank()) {
      age = Integer.parseInt(strAge); // 문자열을 쓸수 없으니 숫자로 바꿈.
    }
    System.out.println("이름: " + name + "나이 :" + age + "살");
  }

  // 2. RequestParam 요청 방식
  // http://localhost:8080/api/users/v1?name="홍길동"&age=30
  @GetMapping("/v2")
  public void RequestParam(
      @RequestParam("name") String name,
      @RequestParam(value = "age", required = false, defaultValue = "00") int age) {
    System.out.println("이름: " + name + "나이 :" + age + "살");
  }

  // 3. 요청파라미터 처리하는 3번째 방법 : Command객체 이용하는 방식
  // command 객체 : 파라미터를 필드로 가지는 객체
  @GetMapping("/v3")
  public void commandObject(UserRequestDTO request) {
    System.out.println(request);
  }

  /*
  Post 방식 요청법
  */

  // 요청 본문 방식 , 요청에 본문을 담아서 보내는 Post 방식
  @PostMapping("/v4")
  public void requestBody(@RequestBody UserRequestDTO request){
    System.out.println(request);
  }

  // 파일 첨부 요청 방식 
  // Method : post , 
  @PostMapping("/v5")
  public void fileAttach(
   @RequestPart("profile") MultipartFile profile, // 파일 받기
   UserRequestDTO request // 일반 텍스트 데이터 받기  

  ){

    if(profile.isEmpty()){
      System.out.println("첨부 파일이 없습니다.");
      return;
    }

    System.out.println("파일명: "+profile.getOriginalFilename());
    System.out.println("첨부 파일의 크기: "+profile.getSize()+"Bytes");
    System.out.println("텍스트 데이터: "+request);
  }

}
