package com.example.data.common;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

//@EntityListeners : 변경,생성 유무를 감시를 하는 감시자 엔티티 변화 감지하는 감시자 찾아 변화를 하는지 찾아보는 것
//AuditingEntityListener.class : `엔티티의 상태 변화`(생성,변경)를 감지하여 `날짜를 자동으로 입력`해준다.

@Getter  
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity  {

  @CreatedDate // 엔티티 생성 시간을 자동으로 저장하는 애노테이션
  // @EnableJpaAuditing (createdDate,LastModifiedDate 사용)
  @Column(updatable = false)
  private LocalDateTime createdAt;

  @LastModifiedDate // 엔티티 값이 변경된 시간을 자동으로 저장 
  private LocalDateTime updatedAt;

}
