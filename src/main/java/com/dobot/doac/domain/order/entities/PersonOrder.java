package com.dobot.doac.domain.order.entities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Table(name = "person_orders")
@AllArgsConstructor
public class PersonOrder {
  @Id
  private UUID id;
  @Getter
  private String name;
  @Getter
  private Long orderAmount;
  @Getter
  private Long adjustedAmount;
  @Getter
  private Long paymentAmount;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;
}