package com.interviews.questions;

import com.google.common.net.InternetDomainName;
import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringTest {

    @Test
    public void test2() {
        String message = "message";
        byte[] bytes = message.getBytes();
        String s = Arrays.toString(bytes);
        System.out.println(Arrays.toString(bytes));
        System.out.println(new String(bytes, StandardCharsets.UTF_8));

    }

    @Test
    public void test3(){
        String s = " a ";
        String s1 = " a bc   ddr";
        int i = s.lastIndexOf(" ", 2);
        int i1 = s1.lastIndexOf(" ", 2);

        String myStr = "Hello planet earth, you are a great planet.";
        System.out.println(myStr.lastIndexOf(" "));
    }

    @Test
    public void setContainAll() {
        List<Integer> integerList = Arrays.asList(1, 2, 3);
        Set<Integer> integerSet = new HashSet<>(integerList);
        boolean b = integerSet.containsAll(integerList);
        boolean b1 = integerSet.containsAll(Arrays.asList(1, 2));
        boolean b2 = integerSet.containsAll(Arrays.asList(1, 2, 3, 4));
    }

    @Test
    public void test() {
        String[] strings = {"AABCDA", "ABCDZADC", "ABCDBCA", "ABCDABDCA"};
        String strToSearch = "ABCD";
        String result = getLongestStr(strings, strToSearch);
        Assert.assertEquals("ABCDABDCA", result);
    }

    @Test
    public void test1() throws MalformedURLException {
                String s1 =" s sd ";
        String trim = s1.trim();
//        String[] split = s.split("\\.");
//        String s1 = split[0];

        URL url = new URL("https://www.google.co.il/ss");
        URL url1 = new URL("https://www.google.com/dd");
        String host = url.getHost();
        String host1 = url1.getHost();
        String s = InternetDomainName.from(host).topPrivateDomain().toString();
    }

    private String getLongestStr(String[] arr, String strToSearch) {
        Set<Integer> charCodeSet = new HashSet<>();
        for (int charCode : strToSearch.toCharArray()) {
            charCodeSet.add(charCode);
        }

        String result = null;
        Integer prev = null;
        for (String s : arr) {
            char[] chars = s.toCharArray();

            for (int i = 0; i < chars.length; i++) {
                if (i > 0)
                    prev = Integer.valueOf(chars[i - 1]);
                int current = chars[i];
                if (prev != null && prev.equals(current))
                    continue;
                if (!charCodeSet.contains(current)) {
                    continue;
                }
                if (result == null || chars.length > result.length())
                    result = s;
            }
        }
        return result;
    }
}
