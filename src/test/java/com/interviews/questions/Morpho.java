package com.interviews.questions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;

public class Morpho {

    /**
     * We have an application that consumes alerts from monitoring systems. After analyzing the data we saw that we have duplicate alerts and we want to filter them out. Implement the logic to determine if an alert should be processed or filtered out.
     *
     * Alert will be filtered out as duplicate if there is already an alert with the same value of “host” and “message” json attributes within the last 30 minutes
     *
     * Alert example:
     * {
     *     "id": "622263f9e489130011e07a69",
     *     "host": "prod-consumer-1",
     *     "message": "CPU Overload",
     *     "status": "critical"
     * }
     *
     * Implement the following filter function that takes a json string as an argument and return true if the alert should be consumed. Assume the function is called for each incoming alert
     *
     * public interface AlertFilter {
     *     public boolean filter(String alertJsonString);
     * }
     *
     * Some extra context:
     * This code will be deployed in production, treat it as such
     * Error handling and readability is very important to us, assuming your code would be reviewed by your peers
     * Don’t worry if you can’t remember syntax by heart, we know about google
     * Assume you have access to all existing libraries and frameworks
     */
    public interface AlertFilter {
        boolean filter(String alertJsonString);
    }

    record Alert(String id, String host, String message, String status, LocalDateTime time) {
    }

    record HostMessage(String host, String message) {
    }

    public class AlertFilterImpl implements AlertFilter {
        Map<HostMessage, Alert> hostMessageToLastAlertMap = new ConcurrentHashMap<>();
//        Map<LocalDateTime, List<HostMessage>> timeToHostMessagesMap = Collections.synchronizedSortedMap(new TreeMap<>()); //in case the order not given, use PriorityBlockingQueue
        PriorityBlockingQueue<Alert> alertsQueueByTime = new PriorityBlockingQueue<>(11, Comparator.comparing(Alert::time));
        ObjectMapper objectMapper = new ObjectMapper();

        private static final Duration maxAllowDuration = Duration.ofMinutes(30);

        @Override
        public boolean filter(String alertJsonString) {
            boolean result;
            Alert alert = readAlert(alertJsonString);
            HostMessage hostMessage = new HostMessage(alert.host(), alert.message());
            Alert existingAlert = hostMessageToLastAlertMap.get(hostMessage);
            LocalDateTime now = LocalDateTime.now();
            if (existingAlert != null && now.minus(maxAllowDuration).isBefore(existingAlert.time)) { //old, need to filter it out
                result = true;
                //replace the last time with the latest time
                if (alert.time.isAfter(existingAlert.time)) {
                    hostMessageToLastAlertMap.put(hostMessage, alert);
//                    timeToHostMessagesMap.remove(existingAlert.time);
                    alertsQueueByTime.remove(existingAlert);
                    alertsQueueByTime.add(alert);
                }
            } else {
                alertsQueueByTime.add(alert);
//                hostMessageToLastAlertMap.computeIfAbsent(hastMessage, v -> new ArrayList<>()).add(existingAlert);
                hostMessageToLastAlertMap.put(hostMessage, existingAlert);
                result = false;
            }
//            timeToHostMessagesMap.computeIfAbsent(now, v -> new ArrayList<>()).add(hostMessage);

            //clean up the old data
            cleanOldData(now);

            return result;
        }

        private void cleanOldData(LocalDateTime now) {
            while (!alertsQueueByTime.isEmpty() && alertsQueueByTime.peek().time().isBefore(now.minus(maxAllowDuration))) {
                Alert oldAlert = alertsQueueByTime.poll();
                HostMessage hostMessage = new HostMessage(oldAlert.host(), oldAlert.message());
                hostMessageToLastAlertMap.remove(hostMessage);
            }
//            Iterator<Map.Entry<LocalDateTime, List<HostMessage>>> iterator = timeToHostMessagesMap.entrySet().iterator();
//
//            while (iterator.hasNext()) {
//                Map.Entry<LocalDateTime, List<HostMessage>> entry = iterator.next();
//                LocalDateTime time = entry.getKey();
//                List<HostMessage> hostMessages = entry.getValue();
//
//                if (time.isBefore(now.minus(maxAllowDuration))) {
//                    hostMessages.forEach(hostMessage1 -> hostMessageToLastAlertMap.remove(hostMessage1));
//                    iterator.remove();
//                } else {
//                    return; //because the map is sorted by time, we can break here
//                }
//            }

//            List<LocalDateTime> keysToRemove = new ArrayList<>();
//            for (Map.Entry<LocalDateTime, List<HostMessage>> entry : timeToHostMessagesMap.entrySet()) {
//                LocalDateTime time = entry.getKey();
//                List<HostMessage> hostMessages = entry.getValue();
//                if (time.isBefore(now.minus(maxAllowDuration))) {
//                    hostMessages.forEach(hostMessage1 -> hostMessageToLastAlertMap.remove(hostMessage1));
//                    keysToRemove.add(time);
//                } else {
//                    break; //because the map is sorted by time, we can break here
//                }
//            }
//            keysToRemove.forEach(timeToHostMessagesMap::remove);
        }

        private Alert readAlert(String alertJsonString) {
            try {
                return objectMapper.readValue(alertJsonString, Alert.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Test
    public void test() {


    }

    public int findDepth(Node root) {
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(root);
        int depthResult = 0;
        int result = 0;
        Set<NodeDepth> nodeDepths = new HashSet<>();
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node.children != null && !node.children.isEmpty()) {
                nodeDepths.add(new NodeDepth(node, depthResult));
                depthResult++;
                result = Math.max(depthResult, result);
                queue.addAll(node.children);
            }
        }

        return result;// nodeDepths.size();

    }

    public class NodeDepth {
        Node node;
        int depth;

        public NodeDepth(Node node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }

    public class Node {
        int data;
        List<Node> children;
    }
}
