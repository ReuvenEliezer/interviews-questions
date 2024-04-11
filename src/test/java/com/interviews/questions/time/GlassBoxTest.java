package com.interviews.questions.time;

import javassist.NotFoundException;
import org.apache.commons.compress.utils.Lists;
import org.junit.Test;

import java.util.*;

public class GlassBoxTest {

    @Test
    public void test() throws NotFoundException {
        /**
         * [1, 1, 3]
         * [4, 1, 6]
         * [3,4 ,5]
         */

        List<Integer> list1 = Lists.newArrayList();
        list1.add(1);
        list1.add(1);
        list1.add(3);

        List<Integer> list2 = Lists.newArrayList();
        list1.add(4);
        list1.add(1);
        list1.add(6);

        List<Integer> list3 = Lists.newArrayList();
        list1.add(3);
        list1.add(4);
        list1.add(5);

        List<List<Integer>> integersLists = new ArrayList<>();
        integersLists.add(list1);
        integersLists.add(list2);
        integersLists.add(list3);


        int integerToSearch = 3;
        int result = calcInstances(integersLists, integerToSearch);
    }

    private int calcInstances(List<List<Integer>> integersLists, int integerToSearch) throws NotFoundException {
        Map<Integer, Integer> valueToCount = new HashMap<>();
        for (List<Integer> integerList : integersLists) {
            Set<Integer> values = new HashSet<>();
            for (Integer value : integerList) {
                if (!values.contains(value)) {
                    valueToCount.merge(value, 1, Integer::sum);
                }
                values.add(value);
            }
        }
        Integer result = valueToCount.get(integerToSearch);
        if (result == null) {
            throw new NotFoundException(String.format("element %s not fount in lists", integerToSearch));
        }

        return result;
    }
}
