package com.example.jpa.ex01_entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.TableGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

  @Id
  @TableGenerator(
    name = "userIdGenerator",
    table = "user_id_seq",
    pkColumnName = "entity",
    pkColumnValue = "User",
    valueColumnName = "nextval",
    initialValue = 0,
    allocationSize = 1
  )
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "userIdGenerator")
  private Long id;
  @Column(nullable = false, length = 100)
  private String username;
  @Column(nullable = false,unique = true ,length = 100)
  private String email;
  @CreationTimestamp // java에서 넣는게 아니고 DB INsert시점에 추가 , 설정 추가 
  @Column(name = "created_at",nullable = false,updatable = false)
  private LocalDateTime createdAt;

  @Enumerated(EnumType.STRING)
  @Column(length = 5)
  private Role role;

  public User(String username, String email,Role role){
    this.username = username;
    this.email = email;
    this.role = role;
    }

    

  @Override
  public String toString() {
    return "User [id=" + id + ", username=" + username + ", email=" + email + ", createdAt=" + createdAt + ", role="
        + role + "]";
  }



  
}
