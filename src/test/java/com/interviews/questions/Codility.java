package com.interviews.questions;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class Codility {

    @Test
    public void maxDistanceBike() {
        //https://www.programmersought.com/article/11471762147/
        Assert.assertEquals(0, maxDistanceBike(new int[]{5, 5}));
        Assert.assertEquals(5, maxDistanceBike(new int[]{-15, -5}));
        Assert.assertEquals(5, maxDistanceBike(new int[]{15, 5}));
        Assert.assertEquals(2, maxDistanceBike(new int[]{10, 0, 8, 2, -1, 12, 11, 3}));

    }

    public int maxDistanceBike(int[] arr) {
        Arrays.sort(arr);
        int ans = Integer.MIN_VALUE;
        if (arr.length == 2) return (arr[1] - arr[0]) / 2;
        for (int i = 0; i < arr.length - 1; i++) {
//If there is an empty position between the two points
//            if (A[i + 1] - A[i] > 1) {
            ans = Math.max(ans, arr[i + 1] - arr[i]);
//            }
        }

        return ans / 2;

    }

}
