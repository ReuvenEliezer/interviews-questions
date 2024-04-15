package com.interviews.questions;

import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ListTest {

    @Test
    public void betterToUseLinkedListWhenMultiAddOrRemoveElementAndBetterToUseArrayListForSearchElementTest() {
        List<String> arrayList = new ArrayList<>();
        arrayList.add("a");
        arrayList.add("c");
        arrayList.add("d");
//        arrayList.add("b");
        arrayList.add(1, "b");

        System.out.println(arrayList);

        LinkedList<String> linkedList = new LinkedList<>();
        arrayList.add("a");
        arrayList.add("c");
        arrayList.add("d");
//        arrayList.add("b");
        arrayList.add(1, "b");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void immutableList() {
        List<String> singletonList = Collections.singletonList("ONE");
        singletonList.add("two");
    }

    @Test
    public void findMedianSortedArraysTest() {
        int[] arr1 = {1, 3};
        int[] arr2 = {2};
        double medianSortedArrays = findMedianSortedArrays(arr1, arr2);
        assertThat(medianSortedArrays).isEqualTo(2);

        assertThat(findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4})).isEqualTo(2.5);

        assertThat(findMedianSortedArrays(new int[]{3}, new int[]{-2, -1})).isEqualTo(-1);

    }

    @Test
    public void merge2SortedArraysTest() {
        int[] arr1 = {1, 3};
        int[] arr2 = {2};
        int[] merge2SortedArray = merge2SortedArrays(arr1, arr2);
        assertThat(merge2SortedArray).containsExactly(1, 2, 3);

        assertThat(merge2SortedArrays(new int[]{3}, new int[]{-2, -1})).containsExactly(-2, -1, 3);

    }

    private int[] merge2SortedArrays(int[] arr1, int[] arr2) {
        if (arr1.length == 0) {
            return arr2;
        }

        if (arr2.length == 0) {
            return arr1;
        }
        int[] mergeSortedArr = new int[arr1.length + arr2.length];
        int lastIndexArr1 = 0;
        int lastIndexArr2 = 0;
        int lastIndexArr3 = 0;

        while (lastIndexArr1 < arr1.length && lastIndexArr2 < arr2.length) {
            int arr1Value = arr1[lastIndexArr1];
            int arr2Value = arr2[lastIndexArr2];

            if (arr1Value < arr2Value) {
                mergeSortedArr[lastIndexArr3] = arr1Value;
                lastIndexArr1++;
            } else {
                mergeSortedArr[lastIndexArr3] = arr2Value;
                lastIndexArr2++;
            }
            lastIndexArr3++;
        }

        while (lastIndexArr1 < arr1.length) {
            mergeSortedArr[lastIndexArr3] = arr1[lastIndexArr1];
            lastIndexArr3++;
            lastIndexArr1++;
        }

        while (lastIndexArr2 < arr2.length) {
            mergeSortedArr[lastIndexArr3] = arr2[lastIndexArr2];
            lastIndexArr3++;
            lastIndexArr2++;
        }

        return mergeSortedArr;
    }

    public double findMedianSortedArrays(int[] arr1, int[] arr2) {
        int[] merge2SortedArrays = merge2SortedArrays(arr1, arr2);
        return findMedian(merge2SortedArrays);
    }

    private static double findMedian(int[] merge2SortedArrays) {
        if (merge2SortedArrays.length % 2 == 0) {
            return (double) (merge2SortedArrays[merge2SortedArrays.length / 2] + merge2SortedArrays[merge2SortedArrays.length / 2 - 1]) / 2;
        }
        return merge2SortedArrays[merge2SortedArrays.length / 2];
    }

//    public double findMedianSortedArrays(int[] arr1, int[] arr2) {
//        List<Integer> mergeSortedList = new ArrayList<>(arr1.length + arr2.length);
//        int lastIndexArr1 = 0;
//        int lastIndexArr2 = 0;
//
//        while (lastIndexArr1 < arr1.length && lastIndexArr2 < arr2.length) {
//            int arr1Value = arr1[lastIndexArr1];
//            int arr2Value = arr2[lastIndexArr2];
//
//            if (arr1Value < arr2Value) {
//                mergeSortedList.add(arr1Value);
//                lastIndexArr1++;
//            } else {
//                mergeSortedList.add(arr2Value);
//                lastIndexArr2++;
//            }
//        }
//
//        while (lastIndexArr1 < arr1.length) {
//            mergeSortedList.add(arr1[lastIndexArr1]);
//            lastIndexArr1++;
//        }
//
//        while (lastIndexArr2 < arr2.length) {
//            mergeSortedList.add(arr2[lastIndexArr2]);
//            lastIndexArr2++;
//        }
//
//
//
//        if (mergeSortedList.size() % 2 == 0) {
//            return (double) (mergeSortedList.get(mergeSortedList.size() / 2) + mergeSortedList.get(mergeSortedList.size() / 2 - 1)) / 2;
//        }
//        return mergeSortedList.get(mergeSortedList.size() / 2);
//    }

}
