package com.interviews.questions;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AlgorithmTest {

    @Test
    public void aVoid() {
        String reportName = "fsdfdf-T6";
        Pattern p = Pattern.compile("[T,t][1-8]");
        Matcher m = p.matcher(reportName);
        boolean b = m.find();
        System.out.println(b);
        String substring = reportName.substring(reportName.length() - 2);
        int[] ints = {7, 1, 5, 3, 6, 4};
        int[] clone = ints.clone();
        clone[0] = 11;
//        int[] ints= {1,2,3,4,5};
//        int[] ints= {7,6,4,3,1};
        int result = maxProfit(ints);
    }

    @Test
    public void reverseStringTest() {
        String str = "qwertyu";
        reverseString(str.toCharArray());

        int[][] mat = {{1, 1, 1, 1, 1, 1}};
        System.out.println(numSubmat(mat));
    }

    public int numSubmat(int[][] mat) {
        int numRows = mat.length;
        int numCols = mat[0].length;
        Map<Integer, Integer> valueToInstancesNumMap = new HashMap<>();
        int max = Math.max(numRows, numCols);
//        for (int i = 0; i < max; i++) {
//
//        }
        for (int i = 0; i < numRows*numCols; i++) {

                int value = mat[numRows-1][numCols-1-i];
                System.out.println(value);
//                if (value == 1) {
//                    Integer integer = valueToInstancesNumMap.get(i);
//                    if (integer == null)
//                        valueToInstancesNumMap.put(i, 1);
//                    else
//                        valueToInstancesNumMap.put(i, integer++);
//                }
//            }

        }

        int result = 0;
//        for (Map.Entry<Integer, Integer> entry :valueToInstancesNumMap.entrySet()         ) {
//            result+=entry.getValue();
//
//        }

        return result;
    }

    public void reverseString(char[] s) {
        for (int i = 0; i < s.length / 2; i++) {
            char temp = s[i];
            s[i] = s[s.length - 1 - i];
            s[s.length - 1 - i] = temp;
        }

        System.out.println(Arrays.toString(s));
    }

    public int maxProfit(int[] prices) {
        int first = prices[0];
        int sumProfit = 0;
//        Map<Integer, List<Integer>> integerListMap =  new HashMap<>();
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > first) {
                sumProfit += prices[i] - first;
            }
            int price = prices[i];

        }
        return -1;
    }
}
