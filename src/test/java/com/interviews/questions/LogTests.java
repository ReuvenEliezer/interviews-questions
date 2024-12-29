package com.interviews.questions;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class LogTests {

    @Test
    public void getMaxLogMessageInLastTimeTest() {
        LogSystem logSystem = new LogSystem(Duration.ofMinutes(1));
        logSystem.addLog("log1");
        logSystem.addLog("log1");
        logSystem.addLog("log2");
        Set<String> mostFrequentLog = logSystem.getMostFrequentLog();
        Set<String> mostFrequentLog1 = logSystem.getMostFrequentLog();
        assertThat(mostFrequentLog).containsExactly("log1");
        assertThat(mostFrequentLog1).containsExactly("log1");
    }

    @Test
    public void getMaxLogMessageInLastTimeTest2() {
        LogSystem logSystem = new LogSystem(Duration.ofMinutes(1));
        logSystem.addLog("log1");
        logSystem.addLog("log1");
        logSystem.addLog("log2");
        logSystem.addLog("log2");
        Set<String> mostFrequentLog = logSystem.getMostFrequentLog();
        assertThat(mostFrequentLog).containsExactlyInAnyOrder("log1", "log2");
        Set<String> mostFrequentLog1 = logSystem.getMostFrequentLog();
        assertThat(mostFrequentLog1).containsExactlyInAnyOrder("log1", "log2");

        logSystem.addLog("log2");
        Set<String> mostFrequentLog2 = logSystem.getMostFrequentLog();
        assertThat(mostFrequentLog2).containsExactlyInAnyOrder("log2");

        logSystem.addLog("log1");
        logSystem.addLog("log1");
        Set<String> mostFrequentLog3 = logSystem.getMostFrequentLog();
        assertThat(mostFrequentLog3).containsExactlyInAnyOrder("log1");
    }

    @Test
    public void getMaxLogMessageInLastTimeTest3() {
        LogSystem logSystem = new LogSystem(Duration.ofMinutes(5));
        logSystem.addLog("log1", LocalDateTime.now().minusMinutes(2));
        logSystem.addLog("log1", LocalDateTime.now().minusMinutes(3));
        logSystem.addLog("log2", LocalDateTime.now());
        assertThat(logSystem.getMostFrequentLog()).containsExactly("log1");
        logSystem.addLog("log2", LocalDateTime.now());
        assertThat(logSystem.getMostFrequentLog()).containsExactlyInAnyOrder("log2", "log1");
        logSystem.addLog("log2", LocalDateTime.now().minusMinutes(6));
        assertThat(logSystem.getMostFrequentLog()).containsExactlyInAnyOrder("log2", "log1");
        logSystem.addLog("log2", LocalDateTime.now());
        assertThat(logSystem.getMostFrequentLog()).containsExactly("log2");
        logSystem.addLog("log1", LocalDateTime.now().minusMinutes(2));
        assertThat(logSystem.getMostFrequentLog()).containsExactlyInAnyOrder("log2", "log1");
    }


    public static class LogSystem {

        private final Duration mostFrequentDuration;

        public LogSystem(Duration mostFrequentDuration) {
            this.mostFrequentDuration = mostFrequentDuration;
        }

        public record LogEntry(String message, LocalDateTime timestamp) {
        }

        private final PriorityQueue<LogEntry> globalLogQueue = new PriorityQueue<>(Comparator.comparing(LogEntry::timestamp));
        private final Map<String, Integer> logFrequency = new HashMap<>();
        private final TreeMap<Integer, Set<String>> frequencyToLogsMap = new TreeMap<>();
        private int maxFrequency;

        public void addLog(String logMessage, LocalDateTime localDateTime) {
            globalLogQueue.add(new LogEntry(logMessage, localDateTime));

            int newFrequency = logFrequency.merge(logMessage, 1, Integer::sum);
            frequencyToLogsMap.computeIfAbsent(newFrequency - 1, k -> new HashSet<>()).remove(logMessage);
            if (frequencyToLogsMap.get(newFrequency - 1).isEmpty()) {
                frequencyToLogsMap.remove(newFrequency - 1);
            }
            frequencyToLogsMap.computeIfAbsent(newFrequency, k -> new HashSet<>()).add(logMessage);
            maxFrequency = Math.max(maxFrequency, newFrequency);

            //only for optimization
            removeOldLogs(localDateTime);
        }

        private void removeOldLogs(LocalDateTime localDateTime) {
            while (!globalLogQueue.isEmpty() && globalLogQueue.peek().timestamp.isBefore(localDateTime.minus(mostFrequentDuration))) {
                LogEntry oldLog = globalLogQueue.poll();
                String oldLogMessage = oldLog.message;
                int oldFrequency = logFrequency.get(oldLogMessage);
                frequencyToLogsMap.get(oldFrequency).remove(oldLogMessage);
                if (frequencyToLogsMap.get(oldFrequency).isEmpty()) {
                    frequencyToLogsMap.remove(oldFrequency);
                }

                frequencyToLogsMap.computeIfAbsent(oldFrequency - 1, k -> new HashSet<>()).add(oldLogMessage);

                if (oldFrequency == 0) {
                    logFrequency.remove(oldLogMessage);
                } else {
                    logFrequency.put(oldLogMessage, oldFrequency - 1);
                }
            }
        }

        public void addLog(String logMessage) {
            addLog(logMessage, LocalDateTime.now());
        }

        public Set<String> getMostFrequentLog() {
            LocalDateTime now = LocalDateTime.now();
            removeOldLogs(now);

//            while (!globalLogQueue.isEmpty() && globalLogQueue.peek().timestamp.isBefore(now.minus(mostFrequentDuration))) {
//                LogEntry oldLog = globalLogQueue.poll();
//                String logMessage = oldLog.message;
//
//                int currentFrequency = logFrequency.getOrDefault(logMessage, 0);
//                if (currentFrequency > 0) {
//
//                    frequencyToLogsMap.get(currentFrequency).remove(logMessage);
//                    if (frequencyToLogsMap.get(currentFrequency).isEmpty()) {
//                        frequencyToLogsMap.remove(currentFrequency);
//                    }
//
//                    int newFrequency = currentFrequency - 1;
//                    if (newFrequency > 0) {
//                        frequencyToLogsMap.computeIfAbsent(newFrequency, k -> new HashSet<>()).add(logMessage);
//                    }
//                    logFrequency.put(logMessage, newFrequency);
//                }
//
//                if (currentFrequency == 1) {
//                    logFrequency.remove(logMessage);
//                }
//            }

            if (frequencyToLogsMap.isEmpty()) {
                return null;
            }
            Map.Entry<Integer, Set<String>> frequencyToLogs = frequencyToLogsMap.lastEntry();
            return frequencyToLogs.getValue();
        }
    }
}
