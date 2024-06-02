package com.interviews.questions;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class GeneralTests {

    @Test
    public void test1() {
        String dateStr = "2020-12-17";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateStr, dateTimeFormatter);

        String s = "sdf/dskkj/2020/2020-10-23/hghfd.csv";
        String s2 = StringUtils.substringBeforeLast(s, ".");
        String s1 = StringUtils.substringBeforeLast(s, "/");
        String result = StringUtils.substringAfterLast(s1, "/");
    }

    @Test
    public void test2() {
        List<String> strings = Arrays.asList("hhf/ds", "df/ff/f");
        System.out.println(Arrays.toString(strings.toArray()));
        strings = strings.stream().map(e -> e + ".csv").toList();
        System.out.println(Arrays.toString(strings.toArray()));
    }

    @Test
    public void test3() {
        List<String> strings = Arrays.asList("1/aaa/ddd");
        Map<String, String> map = new HashMap<>();
        List<String> collect = strings.stream().map(s -> map.put(s, s.toUpperCase())).toList();
        System.out.println(map);
        System.out.println(Arrays.toString(collect.toArray()));
    }

    @Test
    public void test4() {
        Map<String, String> map = new HashMap<>();
        String put = map.put("name", "baeldung");
        Assert.assertNull(put);
    }

    @Test(expected = ConcurrentModificationException.class)
    public void givenIterator_whenFailsFastOnModification_thenCorrect() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "baeldung");
        map.put("type", "blog");

        Set<String> keys = map.keySet();
        Iterator<String> it = keys.iterator();
        map.remove("type");
        while (it.hasNext()) {
            System.out.println(1);
            String key = it.next();
            System.out.println(2);
        }
    }
}
