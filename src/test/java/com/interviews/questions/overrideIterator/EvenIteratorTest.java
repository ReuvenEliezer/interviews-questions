package com.interviews.questions.overrideIterator;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EvenIteratorTest {

    @Test
    public void evenIteratorPrinterTest() {
        List<Integer> integerList = IntStream.range(0, 10).boxed().collect(Collectors.toList());
        com.interviews.questions.overrideIterator.EvenIterator evenIterator = new EvenIterator(integerList);
        while (evenIterator.hasNext()) {
            System.out.println(evenIterator.next());
        }
    }

}
