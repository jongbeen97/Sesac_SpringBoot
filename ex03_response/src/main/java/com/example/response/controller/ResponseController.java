package com.example.response.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.response.dto.UserResponse;

//@Controller
@RestController // controller + responsebody 
@RequestMapping("/api/users/")
public class ResponseController {
  // JSON 문자열 반환(응답)
  @GetMapping("/v1")
 // @ResponseBody  // 반환 값을 view로 해석하지 않고 
  public String responseString(){
    String jsonString = "{\"name\":\"홍길동\",\"age\":30}";
    return jsonString;
  }

  @GetMapping("/v2")
  // @ResponseBody
  public UserResponse responseObject(){
    return new UserResponse("thomas", 30);
  }

  @GetMapping("/v3")
  public ResponseEntity<Map<String,String>> responseEntity(){
    //정상응답 
    // return ResponseEntity.ok(new UserResponse("제시카",20));

    //예외응답
    // return ResponseEntity.badRequest().body(Map.of("message","잘못된 요청"));

    // 예외 응답(일반 예외)
    // return new ResponseEntity<>(Map.of("message","잘못된요청"), HttpStatus.BAD_REQUEST);

    // 예외 상태 변경할 때 
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message","권한없음"));
  }

}
