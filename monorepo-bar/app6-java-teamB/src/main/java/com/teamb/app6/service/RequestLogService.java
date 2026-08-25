package com.teamb.app6.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class RequestLogService {

    private static final int MAX_LOG_SIZE = 100;
    private final LinkedList<RequestLogEntry> requestLogs = new LinkedList<>();

    public void logRequest(String method, String path, String remoteAddr) {
        RequestLogEntry entry = new RequestLogEntry(
            Instant.now().toString(),
            method,
            path,
            remoteAddr
        );

        synchronized (requestLogs) {
            requestLogs.add(entry);
            if (requestLogs.size() > MAX_LOG_SIZE) {
                requestLogs.removeFirst();
            }
        }
    }

    public List<RequestLogEntry> getRecentLogs(int count) {
        synchronized (requestLogs) {
            int size = requestLogs.size();
            int start = Math.max(0, size - count);
            return new ArrayList<>(requestLogs.subList(start, size));
        }
    }

    public int getTotalRequestCount() {
        synchronized (requestLogs) {
            return requestLogs.size();
        }
    }

    public static class RequestLogEntry {
        private String timestamp;
        private String method;
        private String path;
        private String remoteAddr;

        public RequestLogEntry(String timestamp, String method, String path, String remoteAddr) {
            this.timestamp = timestamp;
            this.method = method;
            this.path = path;
            this.remoteAddr = remoteAddr;
        }

        public String getTimestamp() { return timestamp; }
        public String getMethod() { return method; }
        public String getPath() { return path; }
        public String getRemoteAddr() { return remoteAddr; }

        public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
        public void setMethod(String method) { this.method = method; }
        public void setPath(String path) { this.path = path; }
        public void setRemoteAddr(String remoteAddr) { this.remoteAddr = remoteAddr; }
    }
}
