import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class FeedVisorTest {

    @Test
    public void test() {
//        List<Integer> integerList = Arrays.asList(1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 7, 7, 11, 11, 11, 11);
//        List<Integer> integerList = Arrays.asList(21, 1, 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7);
        List<Integer> integerList = Arrays.asList(
        84,
        1,
        1,
        1,
        1,
        2,
        2,
        2,
        3,
        3,
        3,
        3,
        4,
        5,
        5,
        5,
        6,
        6,
        6,
        6,
        7,
        7,
        7,
        8,
        8,
        8,
        8,
        9,
        9,
        9,
        9,
        9,
        10,
        10,
        11,
        11,
        11,
        11,
        11,
        11,
        12,
        12,
        12,
        12,
        12,
        12,
        12,
        13,
        13,
        13,
        13,
        14,
        14,
        14,
        14,
        14,
        16,
        16,
        16,
        16,
        16,
        16,
        17,
        17,
        17,
        18,
        18,
        18,
        18,
        18,
        18,
        18,
        18,
        19,
        19,
        19,
        19,
        19,
        19,
        19,
        20,
        20,
        20,
        20,
        20);
        int result = droppedRequests(integerList);
        System.out.println("result: " + result);
    }

    private static int droppedRequests(List<Integer> integerList) {
        // Write your code here

        Integer max = Collections.max(integerList);
        Map<Integer, Integer> instanceNumCountMaps = new HashMap<>();
        for (int i = 1; i <= max; i++) {
            instanceNumCountMaps.put(i, 0);
        }

        int maxInSameSecond = 3;
        int droppedDueToMaxInSameSecond = 0;
        for (Integer value : integerList) {
            instanceNumCountMaps.put(value, instanceNumCountMaps.get(value) + 1);
        }

        Map<Integer, Integer> baseFilterMap = instanceNumCountMaps.entrySet().stream().parallel()
                .filter(e -> e.getValue() > 0).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

        Map<Integer, Integer> duplicateMoreThan3InstanceInSameTimeMap = baseFilterMap.entrySet().stream().parallel()
                .filter(e -> e.getValue() > maxInSameSecond).collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

        for (Map.Entry<Integer, Integer> entry : duplicateMoreThan3InstanceInSameTimeMap.entrySet()) {
            Integer value = entry.getValue();
            int notValid = value - maxInSameSecond;
            droppedDueToMaxInSameSecond = droppedDueToMaxInSameSecond + notValid;
        }

        //******************//

        int maxInIntervalTimeOf10Sec = 20;
        int timeIntervalInSec = 10;
//        int droppedDueToMaxInIntervalTimeOf10Sec = 0;

        Map<Integer, Integer> timeInterval10SecAggregationMap = new HashMap<>();

        for (Map.Entry<Integer, Integer> entry : instanceNumCountMaps.entrySet()) {
            timeInterval10SecAggregationMap.put(entry.getKey(), 0);
        }

        for (Map.Entry<Integer, Integer> entry : timeInterval10SecAggregationMap.entrySet()) {
            if (instanceNumCountMaps.get(entry.getKey()) == 0) continue;
            Integer sum = 0;
            for (int i = entry.getKey(); i > entry.getKey() - timeIntervalInSec && i > 0; i--) {
                sum = sum + instanceNumCountMaps.get(i);
            }
            timeInterval10SecAggregationMap.put(entry.getKey(), sum);
        }

        Map<Integer, Integer> droppedKeyDueToTimeIntervalOf10Sec = new HashMap<>();

        for (Map.Entry<Integer, Integer> entry : instanceNumCountMaps.entrySet()) {
            Integer aggValue = timeInterval10SecAggregationMap.get(entry.getKey());
            if (aggValue > maxInIntervalTimeOf10Sec) {
                int droppedDueToMaxInIntervalTimeOf10Sec = aggValue - maxInIntervalTimeOf10Sec;
                droppedKeyDueToTimeIntervalOf10Sec.put(entry.getKey(), droppedDueToMaxInIntervalTimeOf10Sec);
            }
        }

//        for (Map.Entry<Integer, Integer> entry : droppedKeyDueToTimeIntervalOf10Sec.entrySet()) {
//            Integer instanceValue = instanceNumCountMaps.get(entry.getKey());
//            int min = Math.min(instanceValue, entry.getValue());
//            droppedKeyDueToTimeIntervalOf10Sec.put(entry.getKey(),min);
//        }


        //******************//


        
        int maxInIntervalTimeOf60Sec = 60;
        int timeIntervalIn60ec = 60;

//        int droppedDueToMaxInIntervalTimeOf60Sec = 0;


        Map<Integer, Integer> timeInterval60SecAggregationMap = new HashMap<>();

        for (Map.Entry<Integer, Integer> entry : instanceNumCountMaps.entrySet()) {
            timeInterval60SecAggregationMap.put(entry.getKey(), 0);
        }


        for (Map.Entry<Integer, Integer> entry : timeInterval60SecAggregationMap.entrySet()) {
            if (instanceNumCountMaps.get(entry.getKey()) == 0) continue;
            Integer sum = 0;
            for (int i = entry.getKey(); i > entry.getKey() - timeIntervalIn60ec && i > 0; i--) {
                sum = sum + instanceNumCountMaps.get(i);
            }
            timeInterval60SecAggregationMap.put(entry.getKey(), sum);
        }


        Map<Integer, Integer> droppedKeyDueToTimeIntervalOf60Sec = new HashMap<>();

        for (Map.Entry<Integer, Integer> entry : instanceNumCountMaps.entrySet()) {
            Integer aggValue = timeInterval60SecAggregationMap.get(entry.getKey());
            if (aggValue > maxInIntervalTimeOf60Sec) {
                int droppedDueToMaxInIntervalTimeOf60Sec = aggValue - maxInIntervalTimeOf60Sec;
                droppedKeyDueToTimeIntervalOf60Sec.put(entry.getKey(), droppedDueToMaxInIntervalTimeOf60Sec);
            }
        }


        //***********************************//
        //reduce duplicate dropped
        Map<Integer, Integer> map = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : duplicateMoreThan3InstanceInSameTimeMap.entrySet()) {
            map.put(entry.getKey(), entry.getValue() - maxInSameSecond);
        }

        for (Map.Entry<Integer, Integer> entry : droppedKeyDueToTimeIntervalOf10Sec.entrySet()) {
            int value = 0;
            if (map.get(entry.getKey()) != null) {
                value = map.get(entry.getKey());
            }
            map.put(entry.getKey(), Math.max(value, entry.getValue()));
        }

        for (Map.Entry<Integer, Integer> entry : droppedKeyDueToTimeIntervalOf60Sec.entrySet()) {
            int value = 0;
            if (map.get(entry.getKey()) != null) {
                value = map.get(entry.getKey());
            }
            map.put(entry.getKey(), Math.max(value, entry.getValue()));
        }


        Integer result = map.values().stream().reduce(Integer::sum).get();
        //expected 67
        return result;
    }

}
