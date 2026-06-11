package com.example.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy // 스프링 객체 만들때 가짜 객체 로 만드는데 이를 허용하는 어노테이션 이다 
// 넣어주는 위치를 configration으로 잡으면 된다. 
// @Configuration 과 함께 두기 
// 여기는 Configuration이 없는데 왜 두었을까? 이게 있으니 두었을 것이다. 이 안에 숨어있는 것이다 (SpringBootApplication합성어이다.)
@SpringBootApplication
public class Ex04AopApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ex04AopApplication.class, args);
	}

}
