package com.payment.procesor.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.payment.procesor.common.PaymentEvent;
import com.payment.procesor.entity.PaymentOutcomeEntity;
import com.payment.procesor.repository.PaymentRepository;

@Service
public class PaymentProcessingService {

 @Autowired
 private PaymentRepository repo;

 public void process(PaymentEvent e) {

	    // ✅ idempotency check (correct)
	    if (repo.existsByPaymentId(e.getPaymentId())) return;

	    long start = System.currentTimeMillis();

	    PaymentOutcomeEntity entity = new PaymentOutcomeEntity();

	    entity.setPaymentId(e.getPaymentId());
	    entity.setDebitAccountId(e.getDebitAccountId());
	    entity.setCreditAccountId(e.getCreditAccountId());
	    entity.setAmount(e.getAmount());
	    entity.setCurrency(e.getCurrency());

	    // ✅ business rule
	    if (e.getAmount().compareTo(new BigDecimal("250000")) > 0) {
	        entity.setStatus("HELD");
	    } else {
	        entity.setStatus("PROCESSED");
	    }

	    entity.setProcessedAt(Instant.now());
	    entity.setProcessingTimeMs(System.currentTimeMillis() - start);

	    repo.save(entity);
	}

 public Page<PaymentOutcomeEntity> getActivity(String status, String accountId, int page, int size) {
	 Pageable pageable = PageRequest.of(page, size, Sort.by("processedAt").descending());

     return repo.findWithFilters(status, accountId, pageable);
 }

  public Map<String, Object> getSummary() {
        Object[] result = repo.getSummaryRaw();
        Map<String, Object> response = new HashMap<>();
        response.put("totalPayments", result[0]);
        response.put("totalAmount", result[1]);
        response.put("startDate", result[2]);
        response.put("endDate", result[3]);
        // status counts (separate queries)
        response.put("processedCount", repo.countByStatus("PROCESSED"));
        response.put("heldCount", repo.countByStatus("HELD"));
        response.put("rejectedCount", repo.countByStatus("REJECTED"));
        return response;
    }


}