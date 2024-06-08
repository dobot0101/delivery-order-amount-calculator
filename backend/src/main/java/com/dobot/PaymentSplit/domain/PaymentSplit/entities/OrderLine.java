package com.dobot.PaymentSplit.domain.PaymentSplit.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_lines")
public class OrderLine {
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
  private LocalDateTime createdAt;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private Order order;
}