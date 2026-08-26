package com.teamb.app2.service;

import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MetricsService {

    private final AtomicLong requestCount = new AtomicLong(0);
    private final AtomicLong processCount = new AtomicLong(0);
    private final AtomicLong calculateCount = new AtomicLong(0);
    private final AtomicInteger errorCount = new AtomicInteger(0);
    private final Instant startTime = Instant.now();

    public void incrementRequestCount() {
        requestCount.incrementAndGet();
    }

    public void incrementProcessCount() {
        processCount.incrementAndGet();
    }

    public void incrementCalculateCount() {
        calculateCount.incrementAndGet();
    }

    public void incrementErrorCount() {
        errorCount.incrementAndGet();
    }

    public long getRequestCount() {
        return requestCount.get();
    }

    public long getProcessCount() {
        return processCount.get();
    }

    public long getCalculateCount() {
        return calculateCount.get();
    }

    public int getErrorCount() {
        return errorCount.get();
    }

    public Instant getStartTime() {
        return startTime;
    }

    public long getUptimeSeconds() {
        return Instant.now().getEpochSecond() - startTime.getEpochSecond();
    }
}
