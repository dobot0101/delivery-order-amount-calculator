package com.dobot.doac.domain.paymentDivision.entities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonPaymentSplit {
  @Id
  private UUID id;
  @Getter
  private String name;
  @Getter
  private Long orderAmount;
  @Getter
  private Long adjustmentAmount;
  @Getter
  private Long paymentAmount;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private PaymentSplit order;
}