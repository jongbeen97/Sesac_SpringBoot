package com.example.jpa.ex04_many_to_one;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "items")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Item {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 100)
  private String ItemName;

  // 연관관계 핵심 부분 
  // 아이템 여러개가 하나의 카테고리에 들어가는 것이다. (다대일 연관 관계로 표현해야 함.)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id" ,foreignKey = @ForeignKey(name = "fk_item_to_category"))
  private Category category;

  public Item(String itemName, Category category) {
    this.ItemName = itemName;
    this.category = category;
  }

  @Override
  public String toString() {
    return "Item [id=" + id + ", ItemName=" + ItemName + ", category=" + category + "]";
  }

  

}
