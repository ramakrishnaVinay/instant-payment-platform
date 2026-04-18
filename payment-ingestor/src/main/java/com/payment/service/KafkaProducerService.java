package com.payment.service;

import com.payment.common.PaymentEvent;
import com.payment.repository.AccountRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class KafkaProducerService {
@Autowired
AccountRepository accountRepo;
 @Autowired
 private KafkaTemplate<String, PaymentEvent> kafkaTemplate;

 public void send(PaymentEvent event) {

   kafkaTemplate.send("payments", event.paymentId, event);
 }
}