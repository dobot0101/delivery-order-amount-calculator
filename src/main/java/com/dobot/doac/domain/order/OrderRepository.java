package com.dobot.doac.domain.order;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dobot.doac.domain.order.entities.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, UUID> {
  
}
