package com.dobot.PaymentSplit.domain.PaymentSplit.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
// 접근 제한자를 private으로 하여 빌더 패턴 강제
@AllArgsConstructor(access = AccessLevel.PRIVATE)
// JPA가 기본 생성자로 엔티티의 인스턴스화를 하기 때문에 기본 생성자를 protected 이상으로 설정
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order {
  @Id
  private UUID id;
  private Long deliveryFee;
  private Long discountAmount;
  private LocalDateTime createdAt;

  @Getter
  @Default
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private final List<OrderLine> orderLines = new ArrayList<>();

  // 서비스 클래스에 위치해야되지 않을까 고민했는데 여러 엔티티간의 상호작용이 필요한 복잡한 로직의 경우 서비스 클래스에 위치하면 될 것 같음
  public Long getTotalOrderAmount() {
    Long totalOrderAmount = 0L;
    for (OrderLine personOrder : orderLines) {
      totalOrderAmount += personOrder.getOrderAmount();
    }
    return totalOrderAmount;
  }

  public void addOrderLines(List<OrderLine> orderLines) {
    this.orderLines.addAll(orderLines);
  }
}