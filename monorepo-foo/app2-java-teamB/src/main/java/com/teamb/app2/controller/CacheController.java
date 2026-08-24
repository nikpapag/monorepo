package com.teamb.app2.controller;

import com.teamb.app2.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/cache")
public class CacheController {

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private DataService dataService;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getCacheStats() {
        Map<String, Object> stats = new HashMap<>();

        cacheManager.getCacheNames().forEach(cacheName -> {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache instanceof CaffeineCache) {
                CaffeineCache caffeineCache = (CaffeineCache) cache;
                com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache =
                    caffeineCache.getNativeCache();

                Map<String, Object> cacheStats = new HashMap<>();
                cacheStats.put("hitCount", nativeCache.stats().hitCount());
                cacheStats.put("missCount", nativeCache.stats().missCount());
                cacheStats.put("hitRate", nativeCache.stats().hitRate());
                cacheStats.put("size", nativeCache.estimatedSize());

                stats.put(cacheName, cacheStats);
            }
        });

        return ResponseEntity.ok(stats);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Map<String, String>> clearAllCaches() {
        dataService.clearAllCaches();

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "All caches cleared");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/clear/{cacheName}")
    public ResponseEntity<Map<String, String>> clearSpecificCache(@PathVariable String cacheName) {
        if ("calculations".equals(cacheName)) {
            dataService.clearCalculationCache();
        } else if ("dataProcessing".equals(cacheName)) {
            dataService.clearDataProcessingCache();
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Unknown cache: " + cacheName);
            return ResponseEntity.badRequest().body(response);
        }

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Cache '" + cacheName + "' cleared");

        return ResponseEntity.ok(response);
    }
}
