package com.dobot.PaymentSplit.domain.PaymentSplit;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dobot.PaymentSplit.domain.PaymentSplit.entities.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, UUID> {

}
