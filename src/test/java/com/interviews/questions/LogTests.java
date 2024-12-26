package com.interviews.questions;

import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class LogTests {

    @Test
    public void getMaxLogMessageInLastTimeTest() {
        LogSystem logSystem = new LogSystem();
        logSystem.addLog("log1");
        logSystem.addLog("log1");
        logSystem.addLog("log2");
        Set<String> mostFrequentLog = logSystem.getMostFrequentLog(Duration.ofMinutes(5));
        Set<String> mostFrequentLog1 = logSystem.getMostFrequentLog(Duration.ofMinutes(5));
        assertThat(mostFrequentLog).containsExactly("log1");
        assertThat(mostFrequentLog1).containsExactly("log1");
    }

    @Test
    public void getMaxLogMessageInLastTimeTest2() {
        LogSystem logSystem = new LogSystem();
        logSystem.addLog("log1");
        logSystem.addLog("log1");
        logSystem.addLog("log2");
        logSystem.addLog("log2");
        Set<String> mostFrequentLog = logSystem.getMostFrequentLog(Duration.ofMinutes(5));
        assertThat(mostFrequentLog).containsExactlyInAnyOrder("log1","log2");
        Set<String> mostFrequentLog1 = logSystem.getMostFrequentLog(Duration.ofMinutes(5));
        assertThat(mostFrequentLog1).containsExactlyInAnyOrder("log1","log2");

        logSystem.addLog("log2");
        Set<String> mostFrequentLog2 = logSystem.getMostFrequentLog(Duration.ofMinutes(5));
        assertThat(mostFrequentLog2).containsExactlyInAnyOrder("log2");

        logSystem.addLog("log1");
        logSystem.addLog("log1");
        Set<String> mostFrequentLog3 = logSystem.getMostFrequentLog(Duration.ofMinutes(5));
        assertThat(mostFrequentLog3).containsExactlyInAnyOrder("log1");
    }

    @Test
    public void getMaxLogMessageInLastTimeTest3() {
        LogSystem logSystem = new LogSystem();
        logSystem.addLog("log1", LocalDateTime.now().minusMinutes(2));
        logSystem.addLog("log1", LocalDateTime.now().minusMinutes(2));
        logSystem.addLog("log2", LocalDateTime.now());
        Set<String> mostFrequentLog = logSystem.getMostFrequentLog(Duration.ofMinutes(5));
        assertThat(mostFrequentLog).containsExactly("log1");
        assertThat(logSystem.getMostFrequentLog(Duration.ofMinutes(1))).containsExactly("log2");
    }


    public static class LogSystem {

        public record LogEntry(String message, LocalDateTime timestamp) {
        }

        private final PriorityQueue<LogEntry> globalLogQueue = new PriorityQueue<>(Comparator.comparing(LogEntry::timestamp));
        private final Map<String, Integer> logFrequency = new HashMap<>();
        private final TreeMap<Integer, Set<String>> frequencyToLogsMap = new TreeMap<>();
        private int maxFrequency;

        public void addLog(String logMessage, LocalDateTime localDateTime) {
            globalLogQueue.add(new LogEntry(logMessage, localDateTime));

            int currentFrequency = logFrequency.getOrDefault(logMessage, 0);
            int newFrequency = currentFrequency + 1;
            logFrequency.put(logMessage, newFrequency);

            frequencyToLogsMap.computeIfAbsent(currentFrequency, k -> new HashSet<>()).remove(logMessage);
            if (frequencyToLogsMap.get(currentFrequency).isEmpty()) {
                frequencyToLogsMap.remove(currentFrequency);
            }
            frequencyToLogsMap.computeIfAbsent(newFrequency, k -> new HashSet<>()).add(logMessage);
            maxFrequency = Math.max(maxFrequency, newFrequency);
        }

        public void addLog(String logMessage) {
            addLog(logMessage, LocalDateTime.now());
        }

        public Set<String> getMostFrequentLog(Duration duration) {
            LocalDateTime now = LocalDateTime.now();

            while (!globalLogQueue.isEmpty() && globalLogQueue.peek().timestamp.isBefore(now.minus(duration))) {
                LogEntry oldLog = globalLogQueue.poll();
                String logMessage = oldLog.message;

                int currentFrequency = logFrequency.getOrDefault(logMessage, 0);
                if (currentFrequency > 0) {

                    frequencyToLogsMap.get(currentFrequency).remove(logMessage);
                    if (frequencyToLogsMap.get(currentFrequency).isEmpty()) {
                        frequencyToLogsMap.remove(currentFrequency);
                    }

                    int newFrequency = currentFrequency - 1;
                    if (newFrequency > 0) {
                        frequencyToLogsMap.computeIfAbsent(newFrequency, k -> new HashSet<>()).add(logMessage);
                    }
                    logFrequency.put(logMessage, newFrequency);
                }

                if (currentFrequency == 1) {
                    logFrequency.remove(logMessage);
                }
            }

            if (frequencyToLogsMap.isEmpty()) {
                return null;
            }
            Map.Entry<Integer, Set<String>> frequencyToLogs = frequencyToLogsMap.lastEntry();
            return frequencyToLogs.getValue();
        }
    }
}
