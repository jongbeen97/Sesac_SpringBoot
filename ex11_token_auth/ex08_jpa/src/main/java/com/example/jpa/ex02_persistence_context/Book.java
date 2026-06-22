package com.example.jpa.ex02_persistence_context;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "books")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본생성자 
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)//AutoIncrement 사용하는 것
  private Long id;
  
  @Column(nullable = false)
  private String title;
  private String author;

  public Book(String title, String author) {
    this.title = title;
    this.author = author;
  }

  @Override
  public String toString() {
    return "Book [id=" + id + ", title=" + title + ", author=" + author + "]";
  }

  // 책 이름을 바꿔주는 비즈니스 메서드 (setter 대신 사람 친화적인 메서드를 만들어서 사용하기를 권장)
  public void changeTitle(String title){
    this.title = title;
  }

  

}
