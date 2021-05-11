import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringTest {

    @Test
    public void setContainAll(){
        List<Integer> integerList = Arrays.asList(1,2,3);
        Set<Integer> integerSet = new HashSet<>(integerList);
        boolean b = integerSet.containsAll(integerList);
        boolean b1 = integerSet.containsAll(Arrays.asList(1,2));
        boolean b2 = integerSet.containsAll(Arrays.asList(1,2,3,4));
    }

    @Test
    public void test() {
        String[] strings = {"AABCDA", "ABCDZADC", "ABCDBCA", "ABCDABDCA"};
        String strToSearch = "ABCD";
        String result = getLongestStr(strings, strToSearch);
        Assert.assertEquals("ABCDABDCA", result);
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
