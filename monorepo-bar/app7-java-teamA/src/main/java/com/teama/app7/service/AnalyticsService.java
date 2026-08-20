package com.teama.app7.service;

import com.teama.app7.model.AnalyticsRequest;
import com.teama.app7.model.AnalyticsResult;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnalyticsService {

    public AnalyticsResult processLargeDataset(AnalyticsRequest request) {
        long startTime = System.currentTimeMillis();

        int dataSize = request.getDataSize() > 0 ? request.getDataSize() : 10000;
        List<Double> data = generateLargeDataset(dataSize);

        double sum = data.stream().mapToDouble(Double::doubleValue).sum();
        double average = sum / data.size();
        double max = data.stream().mapToDouble(Double::doubleValue).max().orElse(0);
        double min = data.stream().mapToDouble(Double::doubleValue).min().orElse(0);

        Map<String, Object> resultData = new HashMap<>();
        resultData.put("dataSize", dataSize);
        resultData.put("sum", sum);
        resultData.put("average", average);
        resultData.put("max", max);
        resultData.put("min", min);

        long processingTime = System.currentTimeMillis() - startTime;
        return new AnalyticsResult("success", resultData, processingTime);
    }

    public AnalyticsResult aggregateData(AnalyticsRequest request) {
        long startTime = System.currentTimeMillis();

        int dataSize = request.getDataSize() > 0 ? request.getDataSize() : 5000;
        List<Map<String, Object>> records = generateRecords(dataSize);

        Map<String, Long> groupCounts = records.stream()
                .collect(Collectors.groupingBy(r -> (String) r.get("category"), Collectors.counting()));

        Map<String, Object> resultData = new HashMap<>();
        resultData.put("totalRecords", records.size());
        resultData.put("groupCounts", groupCounts);

        long processingTime = System.currentTimeMillis() - startTime;
        return new AnalyticsResult("success", resultData, processingTime);
    }

    public double performIntensiveComputation(int iterations) {
        double result = 0.0;
        Random random = new Random();

        for (int i = 0; i < iterations; i++) {
            result += Math.sqrt(random.nextDouble()) * Math.log(i + 1);
            result = result % 1000000;
        }

        return result;
    }

    private List<Double> generateLargeDataset(int size) {
        Random random = new Random();
        return random.doubles(size, 0, 1000).boxed().collect(Collectors.toList());
    }

    private List<Map<String, Object>> generateRecords(int size) {
        Random random = new Random();
        String[] categories = {"A", "B", "C", "D", "E"};
        List<Map<String, Object>> records = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Map<String, Object> record = new HashMap<>();
            record.put("id", i);
            record.put("category", categories[random.nextInt(categories.length)]);
            record.put("value", random.nextDouble() * 100);
            records.add(record);
        }

        return records;
    }
}
