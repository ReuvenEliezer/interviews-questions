package com.interviews.questions;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

public class ArrayTest {

    @Test
    public void test() {
        int[] nums = {1, 1, 2};
        int k = removeDuplicates(nums);
        assertThat(k).isEqualTo(2);
        System.out.println(Arrays.toString(nums));

        nums = new int[]{-3, -1, 0, 0, 0, 3, 3};
        k = removeDuplicates(nums);
        assertThat(k).isEqualTo(4);
        System.out.println(Arrays.toString(nums));
    }

    private int removeDuplicates(int[] nums) {
//        Set<Integer> uniqueNums = new LinkedHashSet<>(Arrays.stream(nums).boxed().collect(Collectors.toSet());

//        Set<Integer> uniqueNums = new HashSet<>();
        int lastPlace = 0;
//        for (int i = 0; i < nums.length; i++) {
//            int num = nums[i];
//            if (!uniqueNums.contains(num)) {
//                uniqueNums.add(num);
//                nums[lastPlace] = num;
//                lastPlace ++;
//            }
//        }

        Integer prevValue = null;
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (prevValue == null || num != prevValue) {
                nums[lastPlace] = num;
                lastPlace++;
            }
            prevValue = num;
        }

//        Iterator<Integer> iterator = uniqueNums.iterator();
//        int index = 0;
//        while (iterator.hasNext()) {
//            Integer next = iterator.next();
//            nums[index] = next;
//            index++;
//        }
//        nums = uniqueNums.stream().mapToInt(Number::intValue).toArray();

        return lastPlace;
    }


    //https://leetcode.com/problems/remove-element/?envType=study-plan-v2&envId=top-interview-150
    @Test
    public void removeElementTest() {
        int[] nums = {3,2,2,3};
        int k = removeElement(nums,3);
        assertThat(k).isEqualTo(2);
    }

    public int removeElement ( int[] nums, int val){
        int index = 0;
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (num != val) {
                nums[index] = num;
                index++;
            }
        }
        return index;
    }

}
