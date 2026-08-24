package com.teamb.app2.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DataService {

    @Cacheable(value = "calculations", key = "#a + '_' + #b")
    public Map<String, Object> performCalculation(int a, int b) {
        simulateExpensiveOperation();

        Map<String, Object> result = new HashMap<>();
        result.put("a", a);
        result.put("b", b);
        result.put("sum", a + b);
        result.put("product", a * b);
        result.put("difference", a - b);
        result.put("quotient", b != 0 ? (double) a / b : null);
        result.put("cached", false);

        return result;
    }

    @Cacheable(value = "dataProcessing", key = "#input")
    public Map<String, Object> processData(String input) {
        simulateExpensiveOperation();

        Map<String, Object> result = new HashMap<>();
        result.put("original", input);
        result.put("processed", input.toUpperCase());
        result.put("length", input.length());
        result.put("reversed", new StringBuilder(input).reverse().toString());
        result.put("cached", false);

        return result;
    }

    @CacheEvict(value = {"calculations", "dataProcessing"}, allEntries = true)
    public void clearAllCaches() {
        // Cache eviction handled by annotation
    }

    @CacheEvict(value = "calculations", allEntries = true)
    public void clearCalculationCache() {
        // Cache eviction handled by annotation
    }

    @CacheEvict(value = "dataProcessing", allEntries = true)
    public void clearDataProcessingCache() {
        // Cache eviction handled by annotation
    }

    private void simulateExpensiveOperation() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
