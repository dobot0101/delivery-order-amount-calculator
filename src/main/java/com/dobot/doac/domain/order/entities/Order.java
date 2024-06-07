package com.dobot.doac.domain.order.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "orders")
@AllArgsConstructor
public class Order {
  @Id
  private UUID id;
  private Long deliveryFee;
  private Long discountAmount;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PersonOrder> personOrders = new ArrayList<>();

  // 서비스 클래스에 위치해야되지 않을까 고민했는데 여러 엔티티간의 상호작용이 필요한 복잡한 로직의 경우 서비스 클래스에 위치하면 될 것 같음
  public Long getTotalOrderAmount() {
    Long totalOrderAmount = 0L;
    for (PersonOrder personOrder : personOrders) {
      totalOrderAmount += personOrder.getOrderAmount();
    }
    return totalOrderAmount;
  }

  public void addPersonOrders(List<PersonOrder> personOrders) {
    for (PersonOrder personOrder : personOrders) {
      this.personOrders.add(personOrder);
    }
  }
}