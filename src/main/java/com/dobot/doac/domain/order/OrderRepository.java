package com.dobot.doac.domain.order;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.dobot.doac.domain.order.entities.Order;

public interface OrderRepository extends CrudRepository<Order, UUID> {
  
}
