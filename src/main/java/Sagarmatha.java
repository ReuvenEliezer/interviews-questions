import lombok.AllArgsConstructor;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class Sagarmatha {


    /**
     * public int rank(int[] arr)
     * <p>
     * 2,4,5,6,5,4,1,4,3,5
     *
     *
     * return the max instances of a value, that is sub array is smallest
     * <p>
     * solution:
     * the max instances (3) is 4, 5 value.
     * the sub-array of 4 is 6 and sub-array of 5 is 7 -> so we need to return the smallest sub-range : value 4

     */

    @Test
    public void test() {
        int[] ints = new int[]{2, 4, 5, 6, 5, 4, 1, 4, 3, 5};
        Assert.assertEquals(4, findValueWithMaxInstanceInSmallestSubArray(ints));
        Assert.assertEquals(6, findMaxInstanceInSmallestSubArray(ints));
    }

    private int findValueWithMaxInstanceInSmallestSubArray(int[] ints) {
        Map<Integer, Data> valueToNunOfInstanceTAndRangeMap = new HashMap<>();

        for (int i = 0; i < ints.length; i++) {
            int finalI = i;
            valueToNunOfInstanceTAndRangeMap.merge(ints[i], new Data(1, i, i), (prev, current) -> {
                prev.totalInstance++;
                prev.endIndex = finalI;
                return prev;
            });
        }

        Integer arrSize = null;
        Integer totalInstances = null;
        Integer value = null;

        for (Map.Entry<Integer, Data> entry : valueToNunOfInstanceTAndRangeMap.entrySet()) {
            Data data = entry.getValue();
            int tempTotalInstances = data.totalInstance;
            if (totalInstances == null || tempTotalInstances > totalInstances) {
                arrSize = data.endIndex - data.startIndex;
                value = entry.getKey();
                totalInstances = data.totalInstance;
            } else if (tempTotalInstances == totalInstances && (data.endIndex - data.startIndex < arrSize)) {
                arrSize = data.endIndex - data.startIndex;
                value = entry.getKey();
            }
        }
        return value;
    }

    private int findMaxInstanceInSmallestSubArray(int[] ints) {
        Map<Integer, Data> valueToNunOfInstanceAndRangeMap = new HashMap<>();

        for (int index = 0; index < ints.length; index++) {
            Data pairTest = valueToNunOfInstanceAndRangeMap.get(ints[index]);
            if (pairTest == null) {
                valueToNunOfInstanceAndRangeMap.put(ints[index], new Data(1, index, index));
            } else {
                pairTest.totalInstance++;
                pairTest.endIndex = index;
            }
        }

        Integer arrSize = null;
        Integer totalInstances = null;
        for (Map.Entry<Integer, Data> entry : valueToNunOfInstanceAndRangeMap.entrySet()) {
            Data data = entry.getValue();
            int tempTotalInstances = data.totalInstance;
            if (totalInstances == null || tempTotalInstances > totalInstances) {
                arrSize = data.endIndex - data.startIndex;
                totalInstances = data.totalInstance;
            } else if (tempTotalInstances == totalInstances && (data.endIndex - data.startIndex < arrSize)) {
                arrSize = data.endIndex - data.startIndex;
            }
        }

        return arrSize;
    }

    @AllArgsConstructor
    static class Data {
        int totalInstance;
        int startIndex;
        int endIndex;
    }
}
