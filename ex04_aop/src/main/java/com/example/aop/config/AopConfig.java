package com.example.aop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.example.aop.advice.TimeTraceAspect;

@Configuration
@EnableAspectJAutoProxy
public class AopConfig {

  @Bean
  public TimeTraceAspect timeTraceAspect(){
    return new TimeTraceAspect();
  }

}
