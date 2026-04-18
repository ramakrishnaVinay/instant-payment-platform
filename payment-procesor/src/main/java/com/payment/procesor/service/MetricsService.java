package com.payment.procesor.service;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service
public class MetricsService {

	private final AtomicLong totalProcessed = new AtomicLong();
    private final AtomicLong totalHeld = new AtomicLong();
    private final AtomicLong totalRejected = new AtomicLong();
    private final AtomicLong totalTime = new AtomicLong();

    public void record(String status, long timeMs) {
        switch (status) {
            case "PROCESSED" -> totalProcessed.incrementAndGet();
            case "HELD" -> totalHeld.incrementAndGet();
            case "REJECTED" -> totalRejected.incrementAndGet();
        }
        totalTime.addAndGet(timeMs);
    }

    public Map<String, Object> summary() {
        long total = totalProcessed.get() + totalHeld.get() + totalRejected.get();
        return Map.of(
                "totalProcessed", totalProcessed.get(),
                "totalHeld", totalHeld.get(),
                "totalRejected", totalRejected.get(),
                "avgProcessingTimeMs", total == 0 ? 0 : totalTime.get() / total
        );
    }
}
