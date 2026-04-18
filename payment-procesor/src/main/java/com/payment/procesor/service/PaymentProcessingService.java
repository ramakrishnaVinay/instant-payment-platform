package com.payment.procesor.service;

import com.payment.procesor.common.PaymentEvent;
import com.payment.procesor.entity.PaymentEntity;
import com.payment.procesor.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;

@Service
public class PaymentProcessingService {

 @Autowired
 private PaymentRepository repo;

 public void process(PaymentEvent e) {
   if (repo.existsById(e.paymentId)) return; // idempotency

   PaymentEntity entity = new PaymentEntity();
   entity.setPaymentId(e.paymentId);
   entity.setAccountId(e.accountId);
   entity.setAmount(e.amount);
   entity.setStatus("APPROVED");
   entity.setCreatedAt(e.createdAt);
   entity.setProcessedAt(Instant.now());

   repo.save(entity);
 }
}