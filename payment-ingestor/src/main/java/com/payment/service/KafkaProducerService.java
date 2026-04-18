package com.payment.service;

import com.payment.common.PaymentEvent;
import com.payment.entity.Account;
import com.payment.entity.Payment;
import com.payment.repository.AccountRepository;
import com.payment.repository.PaymentRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
public class KafkaProducerService {
@Autowired
AccountRepository accountRepo;
@Autowired
PaymentRepository paymentRepo;
 @Autowired
 private KafkaTemplate<String, PaymentEvent> kafkaTemplate;

 public void send(PaymentEvent event) {
     // Find payment by both debit and credit account IDs
     List<Payment> payments = paymentRepo.findByDebitAccountIdAndCreditAccountId(
         event.getDebitAccountId(), event.getCreditAccountId()
     );
     if (payments.isEmpty()) {
         throw new RuntimeException("No payment found for the given debit and credit account IDs");
     }

     // Find the debit account
     Account account = accountRepo.findById(event.getDebitAccountId()).orElse(null);
     if (account == null) {
         throw new RuntimeException("Debit account not found");
     }

     // Only send if account is ACTIVE
     if ("ACTIVE".equals(account.getStatus())) {
         kafkaTemplate.send("payments", event.getPaymentId(), event);
     } else {
         throw new RuntimeException("Account is not active");
     }

 }
}