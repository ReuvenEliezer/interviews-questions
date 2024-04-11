package com.interviews.questions;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class Sagarmatha {


    /**
     * public int rank(int[] arr)
     * <p>
     * 2,4,5,6,5,4,1,4,3,5
     * <p>
     * 3
     * <p>
     * 4 -> 6
     * 5 -> 7
     * <p>
     * 7
     *
     *
     *
     * return the max instances of a value, that is sub array is smallest
     */

    @Test
    public void test() {
        int[] ints = new int[]{2, 4, 5, 6, 5, 4, 1, 4, 3, 5};
        Assert.assertEquals(6, calcMaxInstanceInMInSize(ints));
    }

    private int calcMaxInstanceInMInSize(int[] ints) {
        Map<Integer, PairTest> map = new HashMap<>();

        for (int i = 0; i < ints.length; i++) {
            PairTest pairTest = map.get(ints[i]);
            if (pairTest == null) {
                map.put(ints[i], new PairTest(1, i, i));
            } else {
                pairTest.totalInstance++;
                pairTest.endIndex = i;
            }
        }

        Integer arrSize = null;
        Integer totalInstances = null;
        for (Map.Entry<Integer, PairTest> entry : map.entrySet()) {
            PairTest pairTest = entry.getValue();
            int tempTotalInstances = pairTest.totalInstance;
            if (totalInstances == null || tempTotalInstances > totalInstances) {
                arrSize = pairTest.endIndex - pairTest.startIndex;
                totalInstances = pairTest.totalInstance;
            } else if (tempTotalInstances == totalInstances && (pairTest.endIndex - pairTest.startIndex < arrSize)) {
                arrSize = pairTest.endIndex - pairTest.startIndex;
            }

        }

        return arrSize;
    }

    class PairTest {
        int totalInstance;
        int startIndex;
        int endIndex;

        public PairTest(int totalInstance, int startIndex, int endIndex) {
            this.totalInstance = totalInstance;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }
    }
}
