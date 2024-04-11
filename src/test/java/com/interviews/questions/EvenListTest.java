package com.interviews.questions;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

public class EvenListTest {

    @Test
    public void test() {
        EvenList evenList1 = new EvenList(new Integer[]{2, 4, 5, 3, 7, 8, 9, 10, 7, 13, 14, 19, 10});
        EvenList evenList = new EvenList(new Integer[]{1, 1, 2, 4, 5});
        for (Iterator<Integer> iterator = evenList.iterator(); iterator.hasNext(); ) {
            System.out.println(iterator.next());
        }
    }

    public class EvenList implements Iterable<Integer> {

        private Integer[] arr;


        public EvenList(Integer[] arr) {
            this.arr = arr;
        }

        @Override
        public Iterator<Integer> iterator() {
            //Needs to implement
            return new EvenIterator();
        }

        private class EvenIterator implements Iterator<Integer> {
            private int current = 0;

            @Override
            public boolean hasNext() {
                while (arr.length > current) {
                    if (arr[current] % 2 == 0) {
                        return true;
                    }
                    current++;
                }
                return false;
            }

            @Override
            public Integer next() {
                return arr[current++];
            }
        }

    }

//1,1,2,4,5 ->2,4
//        1,1,3,5,->-
//        11,1,1,1,1,1,1,1,1,1,1,1,4->4

}


