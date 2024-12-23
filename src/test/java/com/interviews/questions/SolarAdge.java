package com.interviews.questions;

import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class SolarAdge {

    @Test
    public void testSolarAdge() {
        int[] arr = {1, 3};
        int result = findSecMax(arr);
        assertThat(result).isEqualTo(1);
        assertThat(findSecMax(new int[]{-10, -1, 3})).isEqualTo(-1);
        assertThat(findSecMax(new int[]{-1, 2, 3})).isEqualTo(2);
        assertThat(findSecMax(new int[]{3, 2, -1})).isEqualTo(2);
    }

    private int findSecMax(int[] arr) {
        if (arr.length < 2) {
            throw new IllegalArgumentException("Array must have at least two elements");
        }
        int max1 = arr[0];
        int max2 = arr[1];

        for (int i = 1; i < arr.length; i++) {
            int value = arr[i];
            if (value > max1) {
                max2 = max1;
                max1 = value;
            }
            if (value > max2 && value < max1) {
                max2 = value;
            }
        }
        return max2;
    }

    /**
     * word of:
     * 1-12
     * 100, 1000, 1000000, 10000000
     * examples:
     * 9
     * 11
     * 25
     * 112
     * 1004
     * 13431
     * 567788
     * 5,555,555
     * <p>
     * print word
     * <p>
     * Your mission is to create a program that receives an Integer as an input and print the number it represents in natural English language.
     * For example: for the input: 1984
     * The output will be: One thousand, nine hundred eighty four.
     * Any integer is a valid input number and should have an output.
     */

    @Test
    public void convertNumberToWordsTest() {
        assertThat(convertNumberToWords(1)).isEqualTo("one");
        assertThat(convertNumberToWords(25)).isEqualTo("twenty five");
        assertThat(convertNumberToWords(1984)).isEqualTo("one thousand nine hundred eighty four");
        assertThat(convertNumberToWords(5555555)).isEqualTo("five million five hundred fifty five thousand five hundred fifty five");
    }

    private static final Map<Integer, String> intToWordMap = createImmutableMap();

    private static Map<Integer, String> createImmutableMap() {
        Map<Integer, String> map = new HashMap<>();
        map.put(0, "zero");
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.put(4, "four");
        map.put(5, "five");
        map.put(6, "six");
        map.put(7, "seven");
        map.put(8, "eight");
        map.put(9, "nine");
        map.put(10, "ten");
        map.put(11, "eleven");
        map.put(12, "twelve");
        map.put(13, "thirteen");
        map.put(14, "fourteen");
        map.put(15, "fifteen");
        map.put(16, "sixteen");
        map.put(17, "seventeen");
        map.put(18, "eighteen");
        map.put(19, "nineteen");
        map.put(20, "twenty");
        map.put(30, "thirty");
        map.put(40, "forty");
        map.put(50, "fifty");
        map.put(60, "sixty");
        map.put(70, "seventy");
        map.put(80, "eighty");
        map.put(90, "ninety");
        map.put(100, "hundred");
        map.put(1000, "thousand");
        map.put(1000000, "million");
        return Collections.unmodifiableMap(map);
    }


    private static String getWordForNumber(int number) {
        return intToWordMap.getOrDefault(number, "");
    }

    private static String convertNumberToWords(int number) {
        StringBuilder words = new StringBuilder();

        if (number / 1000000 > 0) {
            words.append(getWordForNumber(number / 1000000)).append(" million ");
            number %= 1000000;
        }
        if (number / 1000 > 0) {
            words.append(convertNumberToWords(number / 1000)).append(" thousand ");
            number %= 1000;
        }
        if (number / 100 > 0) {
            words.append(getWordForNumber(number / 100)).append(" hundred ");
            number %= 100;
        }
        if (number > 0) {
            if (number <= 20 || number % 10 == 0) {
                words.append(getWordForNumber(number));
            } else {
                words.append(getWordForNumber(number / 10 * 10)).append(" ");
                words.append(getWordForNumber(number % 10));
            }
        }
        return words.toString();
    }

}
