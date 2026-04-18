package com.payment.controller;

import com.payment.common.PaymentEvent;
import com.payment.service.KafkaProducerService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/payments")
public class PaymentController {

 @Autowired
 private KafkaProducerService producer;

 @PostMapping
 public String create(@Valid @RequestBody PaymentEvent event) {
   try {
     producer.send(event);
     return "ACCEPTED";
   } catch (RuntimeException ex) {
     if ("No payment found for the given debit and credit account IDs".equals(ex.getMessage())) {
       throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
     }
     throw ex;
   }
 }
}