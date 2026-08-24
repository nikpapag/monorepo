package com.teamb.app2.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DataServiceTest {

    @Autowired
    private DataService dataService;

    @Autowired
    private CacheManager cacheManager;

    @Test
    public void testPerformCalculation() {
        Map<String, Object> result = dataService.performCalculation(10, 5);

        assertEquals(10, result.get("a"));
        assertEquals(5, result.get("b"));
        assertEquals(15, result.get("sum"));
        assertEquals(50, result.get("product"));
        assertEquals(5, result.get("difference"));
        assertEquals(2.0, result.get("quotient"));
    }

    @Test
    public void testPerformCalculationWithZero() {
        Map<String, Object> result = dataService.performCalculation(10, 0);

        assertEquals(10, result.get("sum"));
        assertNull(result.get("quotient"));
    }

    @Test
    public void testProcessData() {
        Map<String, Object> result = dataService.processData("hello");

        assertEquals("hello", result.get("original"));
        assertEquals("HELLO", result.get("processed"));
        assertEquals(5, result.get("length"));
        assertEquals("olleh", result.get("reversed"));
    }

    @Test
    public void testCachingBehavior() {
        dataService.clearAllCaches();

        long startTime = System.currentTimeMillis();
        dataService.performCalculation(7, 3);
        long firstCallTime = System.currentTimeMillis() - startTime;

        startTime = System.currentTimeMillis();
        dataService.performCalculation(7, 3);
        long secondCallTime = System.currentTimeMillis() - startTime;

        assertTrue(secondCallTime < firstCallTime,
            "Second call should be faster due to caching");
    }

    @Test
    public void testClearAllCaches() {
        dataService.performCalculation(1, 2);
        dataService.processData("test");

        dataService.clearAllCaches();

        assertNotNull(cacheManager.getCache("calculations"));
        assertNotNull(cacheManager.getCache("dataProcessing"));
    }

    @Test
    public void testClearCalculationCache() {
        dataService.performCalculation(1, 2);
        dataService.clearCalculationCache();

        assertNotNull(cacheManager.getCache("calculations"));
    }

    @Test
    public void testClearDataProcessingCache() {
        dataService.processData("test");
        dataService.clearDataProcessingCache();

        assertNotNull(cacheManager.getCache("dataProcessing"));
    }
}
