package com.interviews.questions;

import com.google.common.collect.Lists;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

public class DynamicYieldTest {


    @Test
    @Ignore
    public void returnTheSixthLargestElementTest() {
        List<Integer> integerList = Lists.newArrayList(1, 1, 1, 2, 3, 5, 8, 7, 9, 8, 7, 4, 5, 8, 11, 55, 4);
        List<Integer> integerList2 = IntStream.range(1, 100000000).parallel().boxed().toList();
        integerList.addAll(integerList2);
        int max = 6; //

        int numThreads = 10;
        List<List<Integer>> smallerLists = Lists.partition(integerList2, integerList2.size() / numThreads);

        for (List<Integer> list : smallerLists) {
//            findMax(list)

        }
        int result = findMax(integerList, max);
    }

    private int findMax(List<Integer> integerList, int max) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (Integer value : integerList) {
            if (value > map.size() - max) {
                map.put(value, value);
            }
            if (map.size() > max) {
                Iterator<Map.Entry<Integer, Integer>> iterator = map.entrySet().iterator();
                while (iterator.hasNext() && map.size() > max) {
                    iterator.next();
                    iterator.remove();
                }
            }
        }
        return map.lastKey();
    }

}
