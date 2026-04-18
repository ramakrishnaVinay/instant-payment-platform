package com.payment.controller;

import com.payment.common.PaymentEvent;
import com.payment.service.KafkaProducerService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("/payments")
public class PaymentController {

 @Autowired
 private KafkaProducerService producer;

 @PostMapping
 public String create(@RequestBody PaymentEvent event) {
   producer.send(event);
   return "ACCEPTED";
 }
}