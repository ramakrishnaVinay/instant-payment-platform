package com.payment.procesor.controller;

import com.payment.procesor.entity.PaymentOutcomeEntity;
import com.payment.procesor.repository.PaymentRepository;
import com.payment.procesor.service.PaymentProcessingService;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("/api")
public class ReportingController {

 @Autowired
 private PaymentRepository repo;
 
 @Autowired
 private PaymentProcessingService service;

 @GetMapping("/reports/held")
 public long held() {
   return repo.countByStatus("HELD");
 }
 
 @GetMapping("/reports/summary")
 public Map<String, Object> getSummary() {
     return service.getSummary();
 }
 
 @GetMapping("/activity")
 public Map<String, Object> getActivity(
         @RequestParam(required = false) String status,
         @RequestParam(required = false) String accountId,
         @RequestParam(defaultValue = "0") int page,
         @RequestParam(defaultValue = "10") int size
 ) {

     Page<PaymentOutcomeEntity> result = service.getActivity(status, accountId, page, size);

     Map<String, Object> response = new HashMap<>();
     response.put("page", result.getNumber());
     response.put("size", result.getSize());
     response.put("totalElements", result.getTotalElements());
     response.put("data", result.getContent());

     return response;
 }
}