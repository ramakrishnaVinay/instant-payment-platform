package com.payment.procesor.controller;

import com.payment.procesor.repository.PaymentRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class ReportingController {

 @Autowired
 private PaymentRepository repo;

 @GetMapping("/reports/held")
 public long held() {
   return repo.countByStatus("HELD");
 }
}