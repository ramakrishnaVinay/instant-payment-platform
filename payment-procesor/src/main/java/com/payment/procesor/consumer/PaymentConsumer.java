package com.payment.procesor.consumer;

import com.payment.procesor.common.PaymentEvent;
import com.payment.procesor.service.PaymentProcessingService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
@Component
public class PaymentConsumer {

 @Autowired
 private PaymentProcessingService service;

 @KafkaListener(topics="payments", groupId="processor-group")
 public void consume(PaymentEvent event) {
   service.process(event);
 }
}