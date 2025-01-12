package com.interviews.questions;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class EverC {

    @Test
    public void test() {
        EverCStack everCStack = new EverCStack();
        everCStack.push(8);
        Assert.assertEquals(8, everCStack.getMinValue().intValue());

        everCStack.push(7);
        Assert.assertEquals(7, everCStack.getMinValue().intValue());

        everCStack.push(17);
        everCStack.push(5);
        everCStack.push(5);

        Assert.assertEquals(5, everCStack.getMinValue().intValue());
        everCStack.pop();
        Assert.assertEquals(5, everCStack.getMinValue().intValue());
        everCStack.pop();
        Assert.assertEquals(7, everCStack.getMinValue().intValue());

        everCStack.pop();
        Assert.assertEquals(7, everCStack.getMinValue().intValue());

        everCStack.pop();
        Assert.assertEquals(8, everCStack.getMinValue().intValue());

        everCStack.pop();
        Assert.assertNull(everCStack.getMinValue());

    }

    static class EverCStack extends Stack<Integer> {

        private final TreeMap<Integer, Integer> intToNumOfInstanceMap = new TreeMap<>();

        @Override
        public Integer pop() {
            Integer value = super.pop();
            if (value != null) {
                Integer numOfInstances = intToNumOfInstanceMap.computeIfPresent(value, (a, b) -> b - 1);
                if (numOfInstances.equals(0)) {
                    intToNumOfInstanceMap.remove(value);
                }
            }
            return value;
        }

        @Override
        public Integer push(Integer value) {
            intToNumOfInstanceMap.merge(value, 1, Integer::sum);
            return super.push(value);
        }

        public Integer getMinValue() {
            if (intToNumOfInstanceMap.isEmpty())
                return null;
            return intToNumOfInstanceMap.firstEntry().getKey();
        }
    }
}
