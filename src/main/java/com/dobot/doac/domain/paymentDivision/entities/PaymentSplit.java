package com.dobot.doac.domain.paymentDivision.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentSplit {
  @Id
  private UUID id;
  private Long deliveryFee;
  private Long discountAmount;

  @Getter
  @Default
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<PersonPaymentSplit> personOrders = new ArrayList<>();

  // 서비스 클래스에 위치해야되지 않을까 고민했는데 여러 엔티티간의 상호작용이 필요한 복잡한 로직의 경우 서비스 클래스에 위치하면 될 것 같음
  public Long getTotalOrderAmount() {
    Long totalOrderAmount = 0L;
    for (PersonPaymentSplit personOrder : personOrders) {
      totalOrderAmount += personOrder.getOrderAmount();
    }
    return totalOrderAmount;
  }

  public void addPersonOrders(List<PersonPaymentSplit> personOrders) {
    this.personOrders.addAll(personOrders);
  }
}