package com.dobot.doac.domain.paymentDivision;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dobot.doac.domain.paymentDivision.entities.PaymentSplit;


@Repository
public interface PaymentSplitRepository extends CrudRepository<PaymentSplit, UUID> {
  
}
