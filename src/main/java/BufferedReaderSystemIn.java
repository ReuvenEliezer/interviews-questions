import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class BufferedReaderSystemIn {

    public static void main(String args[]) throws IOException {
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(read.readLine());
        while (t-- > 0) {
            String s = read.readLine();
            Solution ob = new Solution();
            String result = ob.removeDuplicateChar(s);
            System.out.println(result);
        }
    }


    @Test
    public void mergeTwoStr() {
        String[] all = "Hello Bye".split(" ");
        String a = all[0];
        String b = all[1];
        Assert.assertEquals("HBeylelo", mergeTwoStr(a, b));

    }

    private String mergeTwoStr(String a, String b) {
        if (a == null) return b;
        if (b == null) return a;
        int aLength = a.length();
        int bLength = b.length();
        if (aLength == 0) return b;
        if (bLength == 0) return a;

        String big;
        String small;
        if (aLength >= bLength) {
            big = a;
            small = b;
        } else {
            big = b;
            small = a;
        }

        char[] aChars = a.toCharArray();
        char[] bChars = b.toCharArray();
        StringBuilder result = new StringBuilder();
        boolean appendSpace = false;
        for (int i = 0; i < big.length(); i++) {
            if (i < aLength)
                result.append(aChars[i]);
            if (i < bLength) {
                result.append(bChars[i]);
            } else if (!appendSpace) {
//                result.append(" ");
                appendSpace = true;
            }
        }

        return result.toString();
    }

    @Test
    public void removeDuplicateCharTest() {
        Solution ob = new Solution();
        String s = ob.removeDuplicateChar("s");
        int i = ob.twiceCounter(new String[]{"hate", "love", "peace", "love", "peace", "hate", "love", "peace", "love", "peace"});
    }
}

class Solution {


    public String removeDuplicateChar(String s) {
        Set<Integer> integerSet = new LinkedHashSet<>();
        for (int i : s.chars().toArray()) {
            integerSet.add(i);
        }
        StringBuilder result = new StringBuilder();
        for (Integer integer : integerSet) {
            char c = (char) integer.intValue();
            result.append(c);
        }
        return result.toString();
    }

    public int twiceCounter(String strArr[]) {
//        https://practice.geeksforgeeks.org/problems/twice-counter4236/1/?category[]=Hash&category[]=Hash&page=1&query=category[]Hashpage1category[]Hash
        Map<String, Integer> strMap = new HashMap<>();
        for (String s : strArr) {
            Integer count = strMap.get(s);
            if (count == null) {
                count = 0;
            }
            strMap.put(s, ++count);
        }

//        Supplier<Stream<Integer>> streamSupplier = () -> strMap.values().stream().filter(c -> (c > 1));
//        int count = (int) streamSupplier.get().count();
//        return streamSupplier.get().reduce(0, Integer::sum) - count;
        return strMap.values().stream().filter(c -> (c == 2)).reduce(0, Integer::sum) / 2;
    }

}
