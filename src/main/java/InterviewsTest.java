import com.google.common.collect.Sets;

import lombok.ToString;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;

public class InterviewsTest {

    @Test
    public void twoSumTest() {
        //https://leetcode.com/problems/two-sum/
        assertArrayEquals(new int[]{0, 1}, twoSum(new int[]{2, 7, 11, 15}, 9));
        assertArrayEquals(new int[]{1, 2}, twoSum(new int[]{3, 2, 4}, 6));
        assertArrayEquals(new int[]{0, 1}, twoSum(new int[]{3, 3}, 6));
        assertArrayEquals(new int[]{0, 3}, twoSum(new int[]{0, 4, 3, 0}, 0));
        assertArrayEquals(new int[]{0, 2}, twoSum(new int[]{-3, 4, 3, 90}, 0));
        assertArrayEquals(new int[]{2, 4}, twoSum(new int[]{-1, -2, -3, -4, -5}, -8));
    }

    @Test
    public void twoSum2Test() {
        //https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/description/
        assertArrayEquals(new int[]{1, 2}, twoSum2(new int[]{2, 7, 11, 15}, 9));
        assertArrayEquals(new int[]{1, 3}, twoSum2(new int[]{2, 3, 4}, 6));
        assertArrayEquals(new int[]{1, 2}, twoSum2(new int[]{-1, 0}, -1));
    }

    @Test
    public void twoSumBFSTest() {
        //https://leetcode.com/problems/two-sum-iv-input-is-a-bst/description/
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(3);
        root.right = new TreeNode(6);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(4);
        root.right.right = new TreeNode(7);

        assertEquals(true, twoSumBFS(root, 9));
        assertEquals(false, twoSumBFS(root, 28));

        TreeNode root1 = new TreeNode(3);
        root1.left = new TreeNode(1);
        root1.right = new TreeNode(4);
        root1.left.right = new TreeNode(2);
        assertEquals(true, twoSumBFS(root1, 6));

        TreeNode root2 = new TreeNode(1);
        assertEquals(false, twoSumBFS(root2, 2));

        TreeNode root3 = new TreeNode(532);

        root3.left = new TreeNode(278);

        root3.left.right = new TreeNode(397);
        root3.left.right.left = new TreeNode(346);
        root3.left.right.right = new TreeNode(493);
        root3.left.right.right = new TreeNode(504);

        root3.left.left = new TreeNode(115);
        root3.left.left.left = new TreeNode(75);
        root3.left.left.right = new TreeNode(123);
        root3.left.left.right.right = new TreeNode(270);


        root3.right = new TreeNode(676);
        root3.right.left = new TreeNode(629);
        root3.right.left.left = new TreeNode(591);
        root3.right.left.right = new TreeNode(633);
        root3.right.left.right.right = new TreeNode(504);

        root3.right.right = new TreeNode(762);
        root3.right.right.left = new TreeNode(715);
        root3.right.right.left.right = new TreeNode(738);

        root3.right.right.right = new TreeNode(866);
        root3.right.right.right.right = new TreeNode(886);

        assertEquals(true, twoSumBFS(root3, 1159));
        assertEquals(true, twoSumBFS2(root3, 1159, new HashSet<>()));

    }

    private boolean twoSumBFS2(TreeNode root, int target, Set<Integer> valuesSet) {
        if (root == null)
            return false;

        if (!valuesSet.contains(root.val)) {
            int delta = target - root.val;
            if (valuesSet.contains(delta)) {
                return true;
            }
            valuesSet.add(root.val);
        }
        return twoSumBFS2(root.left, target, valuesSet) || twoSumBFS2(root.right, target, valuesSet);
    }

    private boolean twoSumBFS(TreeNode root, int target) {
        //location by depth and direction (right/left)
        Map<Integer, String> numValueToLocationMap = new HashMap<>();
        collectNumsRecursively(root, numValueToLocationMap, 0, Direction.root);

        for (Map.Entry<Integer, String> entry : numValueToLocationMap.entrySet()) {
            Integer numValue = entry.getKey();
            String location = entry.getValue();
            int delta = target - numValue;
            String locationMap = numValueToLocationMap.get(delta);
            if (locationMap != null && !location.equals(locationMap)) {
                return true;
            }
        }
        return false;
    }

    enum Direction {
        root, right, left
    }

    private void collectNumsRecursively(TreeNode root, Map<Integer, String> numValueToLocationMap, int recursiveDepth, Direction direction) {
        if (root == null) {
            return;
        }
        numValueToLocationMap.put(root.val, root.val + "_recursiveDepth:" + recursiveDepth + "_" + direction);
        ++recursiveDepth;
        collectNumsRecursively(root.left, numValueToLocationMap, recursiveDepth, Direction.left);
        collectNumsRecursively(root.right, numValueToLocationMap, recursiveDepth, Direction.right);
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    private int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> valueToIndexMap = new HashMap<>();
        for (int index = 0; index < nums.length; index++) {
            int value = nums[index];
            valueToIndexMap.put(value, index);
        }

        for (int index = 0; index < nums.length; index++) {
            int value = nums[index];
            int delta = target - value;
            Integer indexMap = valueToIndexMap.get(delta);
            if (indexMap != null && !indexMap.equals(index)) {
                return new int[]{index + 1, indexMap + 1};
            }
        }
        return new int[]{};
    }

    private int[] twoSum(int[] nums, int target) {
//        Map<Integer, Set<Integer>> valueToIndexMap = new HashMap<>();
        Map<Integer, Integer> valueToIndexMap = new HashMap<>();


        for (int index = 0; index < nums.length; index++) {
            int value = nums[index];
//            if (value <= target) {
//                valueToIndexMap.computeIfAbsent(value, i -> new HashSet<>()).add(index);
            valueToIndexMap.put(value, index);
//            }
        }

        for (int index = 0; index < nums.length; index++) {
            int value = nums[index];
            int delta = target - value;
//            if (delta >= 0) {
//                Set<Integer> indexes = valueToIndexMap.get(delta);
//                if (indexes != null) {
//                    return new int[]{index, indexes.iterator().next()};
//                }
            Integer indexMap = valueToIndexMap.get(delta);
            if (indexMap != null && !indexMap.equals(index)) {
                return new int[]{index, indexMap};
            }
//            }
        }
        return new int[]{};
    }

    @Test
    public void addTwoNumbersTest() {
        //https://leetcode.com/problems/add-two-numbers/
        ListNode root1 = new ListNode(2);
        root1.next = new ListNode(4);
        root1.next.next = new ListNode(3);

        ListNode root2 = new ListNode(5);
        root2.next = new ListNode(6);
        root2.next.next = new ListNode(4);

        ListNode root = addTwoNumbers(root1, root2);
        assertThat(root.val).isEqualTo(7);
        assertThat(root.next.val).isEqualTo(0);
        assertThat(root.next.next.val).isEqualTo(8);
    }

    private ListNode addTwoNumbers(ListNode root1, ListNode root2) {
        StringBuilder sb1 = new StringBuilder();
        extractNumAsStr(root1, sb1);
        StringBuilder sb2 = new StringBuilder();
        extractNumAsStr(root2, sb2);

        StringBuilder sb1Reverse = reverseLocation(sb1);
        StringBuilder sb2Reverse = reverseLocation(sb2);

        BigInteger sum = new BigInteger(sb1Reverse.toString()).add(new BigInteger(sb2Reverse.toString()));
        ListNode node = buildListNode(sum);
        return node;
    }

    private StringBuilder reverseLocation(StringBuilder sb) {
        char[] numStrArr = sb.toString().toCharArray();
        StringBuilder sbResult = new StringBuilder();
        for (int i = numStrArr.length - 1; i >= 0; i--) {
            sbResult.append(numStrArr[i]);
        }
        return sbResult;
    }

    private ListNode buildListNode(BigInteger sum) {
        char[] numStrArr = sum.toString().toCharArray();
        ListNode node = null;
        ListNode current;
        for (char i = 0; i <= numStrArr.length - 1; i++) {
            int intValue = Character.getNumericValue(numStrArr[i]);
            current = new ListNode(intValue);
            current.next = node;
            node = current;
        }
        return node;
    }

    private void extractNumAsStr(ListNode root, StringBuilder numSb) {
        if (root == null) {
            return;
        }
        extractNumAsStr(root.next, numSb.append(root.val));
    }

    @Test
    public void reverseNumbersTest() {
        //https://leetcode.com/problems/reverse-integer/
        assertThat(reverseInt(123)).isEqualTo(321);
        assertThat(reverseInt(320)).isEqualTo(23);
        assertThat(reverseInt(-321)).isEqualTo(-123);
        assertThat(reverseInt(1534236469)).isEqualTo(0);
    }

    public int reverseInt(int x) {
        String intStr;
        if (x < 0) {
            intStr = String.valueOf(x).substring(1);
        } else {
            intStr = String.valueOf(x);
        }
        char[] chars = intStr.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char i = 0; i < chars.length / 2; i++) {
            char temp = chars[i];
            chars[i] = chars[chars.length - 1 - i];
            int value = Character.getNumericValue(chars[i]);
            sb.append(value);
            chars[chars.length - 1 - i] = temp;
        }

        for (int i = chars.length / 2; i < chars.length; i++) {
            int value = Character.getNumericValue(chars[i]);
            sb.append(value);
        }
        String reversed = new StringBuilder().append(Math.abs(x)).reverse().toString();
        try {
            return x < 0 ? Integer.parseInt(sb.toString()) * -1 : Integer.parseInt(sb.toString());
        } catch (NumberFormatException numberFormatException) {
            return 0;
        }
    }

    @Test
    public void myAtoiTest() {
        //https://leetcode.com/problems/string-to-integer-atoi/
        assertThat(myAtoi("-12a4")).isEqualTo(-12);
        assertThat(myAtoi("00000-42a1234")).isEqualTo(0);
        assertThat(myAtoi("+")).isEqualTo(0);
        assertThat(myAtoi(".1")).isEqualTo(0);
        assertThat(myAtoi("3.14159")).isEqualTo(3);
        assertThat(myAtoi("-91283472332")).isEqualTo(-2147483648);
        assertThat(myAtoi("words and 987")).isEqualTo(0);
        assertThat(myAtoi("  -123")).isEqualTo(-123);
        assertThat(myAtoi("+-123")).isEqualTo(0);
        assertThat(myAtoi("320")).isEqualTo(320);
        assertThat(myAtoi(" 320")).isEqualTo(320);
        assertThat(myAtoi("   +1534236469")).isEqualTo(1534236469);
    }

    public int myAtoi(String s) {
        if (s.equals("")) {
            return 0;
        }
        char[] chars = s.toCharArray();
        boolean isNegative = false;
        StringBuilder sb = new StringBuilder();
        int invalidCounter = 0;

        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            switch (Character.getType(aChar)) {
                case 12:  //" "
                    break;
                case 20: //"-"
                    isNegative = true;
                case 25:  //"+"
                    if (++invalidCounter == 2 && sb.length() == 0) {
                        return 0;
                    }
                    if (i > 0 && Character.isDigit(chars[i - 1])) {
                        if (sb.length() == 0) {
                            return 0;
                        }
                        try {
                            return isNegative ? Integer.parseInt(sb.toString()) * -1 : Integer.parseInt(sb.toString());
                        } catch (NumberFormatException numberFormatException) {
                            return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
                        }
                    }
                    break;
                case 24: //"."
                    if (sb.length() == 0) {
                        return 0;
                    }
                    try {
                        return isNegative ? Integer.parseInt(sb.toString()) * -1 : Integer.parseInt(sb.toString());
                    } catch (NumberFormatException numberFormatException) {
                        return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
                    }
                default:
                    if (Character.isDigit(aChar)) {
                        sb.append(Character.getNumericValue(aChar));
                    } else if (sb.length() == 0) {
                        return 0;
                    } else {
                        try {
                            return isNegative ? Integer.parseInt(sb.toString()) * -1 : Integer.parseInt(sb.toString());
                        } catch (NumberFormatException numberFormatException) {
                            return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
                        }
                    }
            }
        }
        if (sb.length() == 0) {
            return 0;
        }
        try {
            return isNegative ? Integer.parseInt(sb.toString()) * -1 : Integer.parseInt(sb.toString());
        } catch (NumberFormatException numberFormatException) {
            return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
    }

    @Test
    public void lengthOfLongestSubstringTest() {
        LocalDateTime start = LocalDateTime.now();
//https://leetcode.com/problems/longest-substring-without-repeating-characters/submissions/
        assertThat(lengthOfLongestSubstring1("pwwkew")).isEqualTo(3);
        assertThat(lengthOfLongestSubstring1("abcabcbb")).isEqualTo(3);
        String s = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~ abcdefghijklmnopqrstuvwxyzABCD";
        assertThat(lengthOfLongestSubstring1(s)).isEqualTo(95);

        Duration between = Duration.between(start, LocalDateTime.now());
    }

    private int lengthOfLongestSubstring(String str) {
        if (str == null) {
            return 0;
        }
        if (str.length() < 2) {
            return str.length();
        }

        char[] chars = str.toCharArray();
        List<Map<Character, ConsecutiveChar>> charToLongestSubstringMapList = new ArrayList<>(chars.length);

        for (char aChar : chars) {
            //add as a new one
            Map<Character, ConsecutiveChar> charToLongestSubstringMap = new HashMap<>();
            Set<Character> characters = new HashSet<>();
            ConsecutiveChar consecutiveChar = new ConsecutiveChar(characters);
            charToLongestSubstringMap.put(aChar, consecutiveChar);
            charToLongestSubstringMapList.add(charToLongestSubstringMap);

            //add to existing list
            for (Map<Character, ConsecutiveChar> characterSetMap : charToLongestSubstringMapList) {
                characterSetMap.values().forEach(e -> {
                    if (e.isConsecutive) {
                        if (e.characters.contains(aChar)) {
                            e.isConsecutive = false;
                        }
                        e.characters.add(aChar);
                    }
                });
            }
        }

        int longest = 0;
        for (Map<Character, ConsecutiveChar> characterSetMap : charToLongestSubstringMapList) {
            int size = Collections.max(characterSetMap.entrySet(), Comparator.comparingInt(entry -> entry.getValue().characters.size())).getValue().characters.size();
            if (size > longest) {
                longest = size;
            }
        }

        return longest;
    }


    private int lengthOfLongestSubstring1(String str) {
        if (str == null) {
            return 0;
        }
        if (str.length() < 2) {
            return str.length();
        }

        StringBuilder sb = new StringBuilder();

        // Result
        int maxLength = -1;

        for (char c : str.toCharArray()) {
            String current = String.valueOf(c);

            // If string already contains the character
            // Then substring after repeating character
            if (sb.toString().contains(current)) {
                sb = new StringBuilder(sb.substring(sb.indexOf(current) + 1));
            }
            sb.append(c);
            maxLength = Math.max(sb.length(), maxLength);
        }

        return maxLength;
    }

//    private int lengthOfLongestSubstring2(String str) {
//        if (str == null) {
//            return 0;
//        }
//        if (str.length() < 2) {
//            return str.length();
//        }
//
//
//        // Result
//        int maxLength = 0;
//
//        Map<Character, Integer> map = new HashMap<>();
//
//        for (char c : str.toCharArray()) {
//            String current = String.valueOf(c);
//
//            if (map.containsKey(s.charAt(j))) {
//                i = Math.max(map.get(s.charAt(j)), i);
//            }
//            maxLength = Math.max(maxLength, j - i + 1);
//            map.put(s.charAt(j), j + 1);
//            maxLength = Math.max(sb.length(), maxLength);
//        }
//
//        return maxLength;
//    }

    private static class ConsecutiveChar {
        boolean isConsecutive = true;
        Set<Character> characters;

        public ConsecutiveChar(Set<Character> characters) {
            this.characters = characters;
        }
    }

    @Test
    public void trappingWaterTest() {
        //https://practice.geeksforgeeks.org/problems/trapping-rain-water-1587115621/1
        //https://www.techiedelight.com/trapping-rain-water-within-given-set-bars/

        assertEquals(10, trappingWater(new int[]{3, 0, 0, 2, 0, 4}));
        assertEquals(10, trappingWater(new int[]{7, 4, 0, 9}));
        assertEquals(0, trappingWater(new int[]{6, 9, 9}));

        assertEquals(25, trappingWater(new int[]{7, 0, 4, 2, 5, 0, 6, 4, 0, 5}));
    }

    static int map(int n, String keys[], int arr[], String s) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < keys.length; i++) {
            map.put(keys[i], arr[i]);
        }
        return map.get(s);
        // code here
    }

    private static long trappingWater(int[] arr) {
        if (arr.length < 2) return 0;

        int startIndex = 0;
        int endIndex = arr.length - 1;

        int startHeight = arr[0];
        int endHeight = arr[endIndex];


        long totalWaterAmount = 0;
        while (startIndex < endIndex) {
            if (arr[startIndex] <= arr[endIndex]) {
                startIndex++;
                startHeight = Math.max(startHeight, arr[startIndex]);
                totalWaterAmount += startHeight - arr[startIndex];
            } else {
                endIndex--;
                endHeight = Math.max(endHeight, arr[endIndex]);
                totalWaterAmount += endHeight - arr[endIndex];
            }
        }

        return totalWaterAmount;
    }

    @Test
    public void test() {
        List<Integer> integers1 = Arrays.asList(1);
        Set<Integer> integers2 = new HashSet<>(Arrays.asList(2, 1));
        boolean contains = integers1.contains(1);
        boolean b = integers1.retainAll(integers2);
    }

    @Test
    public void zoomIntoTest() {
//        נתון מערך לדוג':
//        אם תוצאת הסכום של 2 אברים במערך קיימת במערך - יש להדפיסה.
//                לדוג' 1+3 = 4
//        4 קיים במערך.
        int[] arr = {1, 3, 4, 8, 2, 6, 10, 7};
        // printSumOfPairIfExistInArr(arr);
        List<AmazonTest.Pair> result = getSumOfPairIfExistInArr(arr);
        assertEquals(6, result.size());
    }

    private List<AmazonTest.Pair> getSumOfPairIfExistInArr(int[] arr) {
        List<AmazonTest.Pair> result = new ArrayList<>();
        Map<Integer, Boolean> integersPrinted = new HashMap<>();
        for (int k : arr)
            integersPrinted.put(k, false);
        int i1 = 0;
        for (int i = 0; i < arr.length; i++) {
            int first = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                int second = arr[j];
                int sum = first + second;
                if (integersPrinted.containsKey(sum)) {
                    Boolean printed = integersPrinted.get(sum);
                    if (!printed) {
                        result.add(new AmazonTest.Pair(first, second));
                        integersPrinted.put(sum, true);
                        System.out.println(first + " " + second + " = " + sum);
                    }
                }
                i1++;
            }
        }

        System.out.println(i1);
        return result;

    }


    @Test
    public void binarySearchTest() {
        int[] nums = {1, 2, 5, 9, 11, 22, 33, 55}; //must be sorted list
        int target = 2;
        System.out.println(Arrays.toString(nums));

        int result = binarySearch(nums, target);
        System.out.println("result: " + result);
        int binaryResult = binarySearchRecursive(nums, target, 0, nums.length);
        System.out.println("binaryResult: " + binaryResult);
    }

    private int binarySearch(int[] nums, int target) {
        int start = 0, end = nums.length - 1;
        int mid = (start + end) / 2;

        while (start <= end) {
            if (target == nums[mid])
                return mid;

            if (target < nums[mid]) {
                mid--;
            } else {
                mid++;
            }
        }
        return -1;
    }

    @Test
    public void nsoTest() {
        // מקבלים מספר ובודקים האם הוא מספר מושלם - במידה וכן מחזירים את המספרים שמרכיבים אותו ככזה
//        https://he.wikipedia.org/wiki/%D7%9E%D7%A1%D7%A4%D7%A8_%D7%9E%D7%A9%D7%95%D7%9B%D7%9C%D7%9C#cite_note-2
        StopWatch watch = new StopWatch();
        watch.start("for each");
        long n = 8589869056l;
        List<Long> forEachResult = calcAllPerfectNumbersUpTo_ForEach(n);
        watch.stop();
        watch.start("map");
        List<Long> mapReduceResult = calcAllPerfectNumbersUpTo_MapReduce(n);
        watch.stop();
        System.out.println("forEachResult: " + forEachResult.size());
        System.out.println("mapReduceResult: " + mapReduceResult.size());
        System.out.println(watch.getTotalTimeSeconds());
        System.out.println(watch.prettyPrint());
        assertEquals(forEachResult.size(), mapReduceResult.size());
        assertEquals(forEachResult, mapReduceResult);
    }

    @Test
    public void maxDepthTree_Test() {
        BinaryTree tree = new BinaryTree();

        tree.root = new Node(1);
        tree.root.left = new Node(2);
        tree.root.right = new Node(3);
        tree.root.left.left = new Node(4);
        tree.root.left.right = new Node(5);

        System.out.println("Height of tree is : " +
                tree.maxDepth(tree.root));
        tree.sumValues(tree.root);
        System.out.println(result);
    }

    class Node {
        int data;
        Node left, right;

        Node(int item) {
            data = item;
            left = right = null;
        }
    }

    class BinaryTree {
        Node root;

        /* Compute the "maxDepth" of a tree -- the number of
           nodes along the longest path from the root node
           down to the farthest leaf node.*/
        int maxDepth(Node node) {
            if (node == null)
                return 0;
            /* compute the depth of each subtree */
            int lDepth = maxDepth(node.left);
            int rDepth = maxDepth(node.right);

            /* use the larger one */
            if (lDepth > rDepth)
                return (lDepth + 1);
            else
                return (rDepth + 1);
        }

        public int sumValues(Node root) {
            if (root == null)
                return 0;
            result += root.data;
            return sumValues(root.left) + sumValues(root.right);
        }
    }

    int result;

    @Test
    public void binaryTreeReturnAllValuesAsListFromLeftToRightPostOrderTest() {
        /**
         * https://practice.geeksforgeeks.org/problems/postorder-traversal-iterative/1/?problemStatus=unsolved&difficulty[]=1&page=1&query=problemStatusunsolveddifficulty[]1page1
         */
        BinaryTree tree = new BinaryTree();

        tree.root = new Node(1);
        tree.root.left = new Node(2);
        tree.root.right = new Node(3);
        tree.root.left.left = new Node(4);
        tree.root.left.right = new Node(5);

        List<Integer> integers = postOrder(tree.root);
        assertEquals(IntStream.of(4, 5, 2, 3, 1).boxed().collect(Collectors.toList()), integers);

        List<Integer> integers2 = postOrderWithoutRecursive(tree.root);
        assertEquals(IntStream.of(4, 5, 2, 3, 1).boxed().collect(Collectors.toList()), integers2);

    }

    @Test
    public void binaryTreeReturnAllValuesAsListFromLeftToRightInOrderTest() {
        /**
         * https://practice.geeksforgeeks.org/problems/inorder-traversal-iterative/1/?problemStatus=unsolved&difficulty[]=1&page=1&query=problemStatusunsolveddifficulty[]1page1
         */
        BinaryTree tree = new BinaryTree();

        tree.root = new Node(1);
        tree.root.left = new Node(2);
        tree.root.right = new Node(3);
        tree.root.left.left = new Node(4);
        tree.root.left.right = new Node(5);

        List<Integer> integers = inOrder(tree.root);
        assertEquals(IntStream.of(4, 2, 5, 1, 3).boxed().collect(Collectors.toList()), integers);

        List<Integer> integers2 = inOrderWithoutRecursive(tree.root);
        assertEquals(IntStream.of(4, 2, 5, 1, 3).boxed().collect(Collectors.toList()), integers2);

    }

    private List<Integer> inOrderWithoutRecursive(Node root) {
        List<Integer> result = new ArrayList<>();
        Stack<Node> queue = new Stack<>();
        Node curr = root;
        while (curr != null || !queue.isEmpty()) {
            while (curr != null) {
                queue.add(curr);
                curr = curr.left;
            }

            curr = queue.pop();
            result.add(curr.data);

            curr = curr.right;
        }
        return result;
    }

    private List<Integer> inOrder(Node root) {
        List<Integer> result = new ArrayList<>();
        doRecursiveInOrder(root, result);
        return result;
    }

    private void doRecursiveInOrder(Node node, List<Integer> result) {
        if (node == null) return;
        if (node.left != null) {
            doRecursiveInOrder(node.left, result);
        }
        result.add(node.data);
        if (node.right != null) {
            doRecursiveInOrder(node.right, result);
        }
    }

    private List<Integer> postOrderWithoutRecursive(Node root) {
        List<Integer> result = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()) {
            Node temp = stack.pop();
            result.add(temp.data);
            if (temp.left != null)
                stack.push(temp.left);
            if (temp.right != null)
                stack.push(temp.right);
        }
        reverseList(result);
        return result;
    }

    private void reverseList(List<Integer> result) {
        for (int i = 0; i < result.size() / 2; i++) {
            int tempValue = result.get(i);
            result.set(i, result.get(result.size() - 1 - i));
            result.set(result.size() - 1 - i, tempValue);
        }
    }

    private List<Integer> postOrder(Node node) {
        List<Integer> result = new ArrayList<>();
        doRecursivePostOrder(node, result);
        return result;
    }

    private void doRecursivePostOrder(Node node, List<Integer> result) {
        if (node == null) return;
        if (node.left != null) {
            doRecursivePostOrder(node.left, result);
        }
        if (node.right != null) {
            doRecursivePostOrder(node.right, result);
        }
        result.add(node.data);
    }


    @Test
    public void reverseArray_Test() {
        System.out.println("Before reversing: ");
        int[] ints = {11, 22, 33, 44, 55};
        System.out.println(Arrays.toString(ints));
        for (int i = 0; i < ints.length / 2; i++) {
            int temp = ints[i];
            ints[i] = ints[ints.length - 1 - i];
            ints[ints.length - 1 - i] = temp;
        }

        System.out.println("After reversing: ");
        System.out.println(Arrays.toString(ints));


        System.out.println();
        for (int i = ints.length - 1; i >= 0; i--) {
            System.out.println(ints[i]);
        }
    }

    @Test
    public void removeDuplicatesIntArr_Test() {
        int[] ints = {5, 5, 1, 2};
        List<Integer> integerHashSet = new ArrayList<>();
        // HashSet<Integer> indexToRemove = new HashSet<>();

        for (int value : ints) {
            if (!integerHashSet.contains(value)) {
                // indexToRemove.add(value);
                // } else {
                integerHashSet.add(value);
            }
        }

        // Collections.sort(integerHashSet);
//        for (Integer integer : indexToRemove) {
//            ints = ArrayUtils.removeElement(ints, integer);
//        }
        // Iterator<Integer> iterator = integerHashSet.iterator();
        // int index = 0;
        // while (iterator.hasNext()) {
        //     Integer next = iterator.next();
        //     ints[index] = next;
        //     index++;
        // }

        ints = integerHashSet.stream().mapToInt(Integer::intValue).toArray();
//        for (int i = 0; i < integerHashSet.size(); i++) {
//            ints[i] = integerHashSet.ge
//            ints[] =ArrayUtils.removeElement(ints, integer);
//        }
    }


    @Test
    public void arrayListVsLinkedList_MaxSizeTest() {
        Integer[] myArray = {10, 20, 30};
        Integer result = Arrays.stream(myArray)
                .reduce(0, (a, b) -> (a + b) / myArray.length

                );

//        https://stackoverflow.com/questions/3767979/how-much-data-can-a-list-can-hold-at-the-maximum
        int maxArrayValue = Integer.MAX_VALUE;
        List<Long> arrayList = new ArrayList<>();
        List<Long> linkedList = new LinkedList<>();

//        LongStream.range(0, maxArrayValue-1000000).parallel().forEach(i -> {
//            arrayList.add(i);
//            linkedList.add(i);
//        });
        for (long i = 0; i < maxArrayValue; i++) {
            arrayList.add(i);
//            linkedList.add(i);
        }
        int u = 0;
    }

    @Test
    public void intSumTest() {
        int maxArrayValue = Integer.MAX_VALUE;
        AtomicInteger count = new AtomicInteger();
        IntStream.range(0, maxArrayValue).parallel().forEach(i -> count.getAndIncrement());
        int result = count.get();
        System.out.println("result: " + result);
        count.getAndIncrement();
        int resultAfterCountIsOverMaxValueOfInt = count.get();
        System.out.println("resultAfterCountIsOverMaxValueOfInt: " + resultAfterCountIsOverMaxValueOfInt);
    }

    public List<Long> calcAllPerfectNumbersUpTo_ForEach(long n) {
        List<Long> result = new ArrayList<>();
        long sum = 0;
        for (long i = 1; i < n; i++) {
            if (n % i == 0) {
                sum += i;
                result.add(i);
            }
        }
        if (sum == n) return result;
        return new ArrayList<>();
    }

    public List<Long> calcAllPerfectNumbersUpTo_MapReduce(long n) {
        Supplier<Stream<Long>> boxed = () -> LongStream.range(1l, n).parallel().filter(i -> (n % i == 0)).boxed();
        if (boxed.get().parallel().reduce(0l, Long::sum) == n)
            return boxed.get().parallel().collect(Collectors.toList());
        return new ArrayList<>();
    }

    private static int binarySearchRecursive(int[] nums, int target, int start, int end) {
        if (start > end) {
            return -1;
        }
        int mid = (start + end) / 2;
        if (target == nums[mid])
            return mid;

        if (target < nums[mid])
            return binarySearchRecursive(nums, target, start, mid--);

        return binarySearchRecursive(nums, target, mid++, end);
    }

    @Test
    public void matrixSearchTest() {
        int[][] matrix = {
                {1, 5, 9, 11},
                {13, 16, 19, 24},
                {28, 30, 38, 50}
        }; //must be sorted list
        int target = 13;
        System.out.println(Arrays.deepToString(matrix));
        System.out.println("result: " + matrixSearch(matrix, target));
    }

    private boolean matrixSearch(int[][] matrix, int target) {
        if (matrix.length == 0) {
            return false;
        }

        int numRows = matrix.length;
        int numCols = matrix[0].length;

        int left = 0, right = numRows * numCols - 1;

        int mid = (left + right) / 2;
        while (left <= right) {
            int midValue = matrix[mid / numCols][mid % numCols];
//            int midValue = matrix[mid / numRows][mid / numCols];
            if (target == midValue) {
                return true;
            }
            if (target < midValue) {
                right = mid--;
            } else {
                left = mid++;
            }
        }

        return false;
    }

    @Test
    public void test12() {
        List<Integer> numbers = Arrays.asList(5, 3, 50, 24, 40, 2, 9, 18);
        Stream<Integer> sorted = numbers.stream()
                .sorted(Comparator.naturalOrder());
        numbers.stream().sorted(Integer::compareTo);

    }

    @Test
    public void bubbleSortTest() {
        int[] arr = {1, 3, 4, 8, 2, 6, 10, 7};
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void microsoftDamkaTest() {
        // Damka
        // move up right or up left
        // do not exceed board
        // must move to empty space on board
        // must move over enemy pawns
        // can move over 1 or more enemy pawns
        // after successfully moving, can move again but only over enemy pawns
        // jaffar has only one pawn! find it, then calculate
        // The board is n*n in size
        // Jaffar      - 'O'
        // Allading    - 'X'
        // empty space - '.'
        String[] matrixStrArr = {
                "..X...",
                "......",
                "....X.",
                ".X....",
                "..X.X.",
                "...O.."
        };

        microsoftDamka(matrixStrArr);
    }

    private int microsoftDamka(String[] matrixStrArr) {
        Position japperPosition = findJaffar(matrixStrArr);
        if (japperPosition == null)
            return 0; //not found Japper;

        Position japperTempPosition = japperPosition;
        eat(matrixStrArr, japperPosition, 0);
//        int sumLeft = canMoveUpperLeft(matrixStrArr, japperTempPosition, 0);
//        int sumRight = canMoveUpperRight(matrixStrArr, japperTempPosition, 0);

//        if (sumLeft>)
        return 0;
    }

    private int eat(String[] matrixStrArr, Position position, int sum) {
        //TODO impl
        if (position.y - 1 < 0)
            return sum;
        if (canMoveUpperLeft(matrixStrArr, position)) return sum;
        if (canMoveUpperRight(matrixStrArr, position)) return sum;
        return sum;
    }

    private boolean canMoveUpperRight(String[] matrixStrArr, Position position) {

//        if (position.y - 1 < 0)
//            return sum;
        if (position.x + 1 < 0)
            return false;
        return true;
//        String y = matrixStrArr[position.y - 1];
//
//        char c = y.charAt(position.x + 1);
//        if (c == 'X' && position.y - 1>=0) {
//            return canMoveUpperRight(matrixStrArr, new Position(position.x + 1 ,position.y - 1 ), sum++);
//        }
//        return sum;
    }

    private boolean canMoveUpperLeft(String[] matrixStrArr, Position position) {

        if (position.x - 1 < 0)
            return false;

        return true;
//        String y = matrixStrArr[position.y - 1];
//
//        char c = y.charAt(position.x - 1);
//        if (c == 'X') {
//            return canMoveUpperLeft(matrixStrArr, new Position(position.x - 1 ,position.y - 1 ), sum++);
//        }
//        return sum;
    }

    private Position findJaffar(String[] matrixStrArr) {
        for (int yPosition = 0; yPosition < matrixStrArr.length; yPosition++) {
            String s = matrixStrArr[yPosition];
            for (int xPosiotion = 0; xPosiotion < s.length(); xPosiotion++) {
                int charValue = s.charAt(xPosiotion);
                if ('O' == charValue)
                    return new Position(xPosiotion, yPosition);
            }
        }
        return null;
    }

    public class Position {
        public int x;
        public int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

    }

    @Test
    public void microsoftCharacterTest() {
//        char[][] matrixChars = {
//                {'.', '.', 'X', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', 'X', '.'},
//                {'.', 'X', '.', '.', '.', '.'},
//                {'.', '.', 'X', '.', 'X', '.'},
//                {'.', '.', '.', 'O', '.', '.'}
//        };
//        char[][] matrixChars = {
//                {'.', '.', 'X', '.', '.', '.'},
//                {'.', '.', '.', '.', '.', '.'},
//                {'.', '.', '.', '.', 'X', '.'},
//                {'.', 'X', '.', '.', '.', '.'},
//                {'.', '.', 'X', '.', 'X', '.'},
//                {'.', '.', '.', 'O', '.', '.'}
//        };

        String s = "WeTestCodErs";
//        String s = "Tt";

        String result = solution(s);
        System.out.println("result: " + result);
    }

    // find max letter that appears both in upper and lower case
    public String solution(String str) {
//        HashSet<Character> characterHashSet = new LinkedHashSet<>();
//        for (char ch : str.toCharArray())
//            characterHashSet.add(ch);
//        int diffFromUpperToLowerChar = 'Z' - 'z';
//        // From Z to A
//        for (int i = 'Z'; i >= (int) 'A'; i--) {
//            char upperCase = (char) i;
//            if (characterHashSet.contains(upperCase)) {
//                char lowerCase = (char) (i - diffFromUpperToLowerChar);
//                if (characterHashSet.contains(lowerCase))
//                    return String.valueOf(upperCase);
//            }
//        }
//        return "NO";


        HashSet<Integer> characterHashSet = new LinkedHashSet<>();
        for (int ch : str.toCharArray()) {
            if (characterHashSet.size() > 26 * 2) //optimization - all character type A-Z a-z
                break;
            characterHashSet.add(ch);
        }

        int diffFromUpperToLowerChar = 'z' - 'Z';
        // From Z to A
        for (int upperCaseIndex = 'Z'; upperCaseIndex >= 'A'; upperCaseIndex--) {
            if (characterHashSet.contains(upperCaseIndex)) {
                int lowerCase = upperCaseIndex + diffFromUpperToLowerChar;
                if (characterHashSet.contains(lowerCase))
                    return String.valueOf((char) upperCaseIndex);
            }
        }
        return "NO";
    }

    @Test
    public void balancedParenthesesExpression_Test() {
//        String expression= "[()]{}{[()()]()}";
        String expression = "}";
        boolean balancedParenthesesExpression = isBalancedParenthesesExpression(expression);
    }

    public boolean isBalancedParenthesesExpression(String expression) {
        HashMap<Character, Character> mappings = new HashMap<>();
        mappings.put(')', '(');
        mappings.put('}', '{');
        mappings.put(']', '[');
        // Initialize a stack to be used in the algorithm.
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // If the current character is a closing bracket.
            if (mappings.containsKey(c)) {

                // Get the top element of the stack. If the stack is empty, set a dummy value of '#'

                // If the mapping for this bracket doesn't match the stack's top element, return false.
                if (stack.empty() || stack.pop() != mappings.get(c)) {
                    return false;
                }
            } else {
                // If it was an opening bracket, push to the stack.
                stack.push(c);
            }
        }

        // If the stack still contains elements, then it is an invalid expression.
        return stack.isEmpty();
    }

    @Test
    public void pivotIndexTest() {
        /**
         * https://leetcode.com/interview/1/
         Given an array of integers nums, write a method that returns the "pivot" index of this array.

         We define the pivot index as the index where the sum of all the numbers to the left of the index is equal to the sum of all the numbers to the right of the index.

         If no such index exists, we should return -1. If there are multiple pivot indexes, you should return the left-most pivot index.



         Example 1:

         Input: nums = [1,7,3,6,5,6]
         Output: 3
         Explanation:
         The sum of the numbers to the left of index 3 (nums[3] = 6) is equal to the sum of numbers to the right of index 3.
         Also, 3 is the first index where this occurs.
         Example 2:

         Input: nums = [1,2,3]
         Output: -1
         Explanation:
         There is no index that satisfies the conditions in the problem statement.


         Constraints:

         The length of nums will be in the range [0, 10000].
         Each element nums[i] will be an integer in the range [-1000, 1000]
         */
//        int[] ints = {1, 7, 3, 6, 5, 6};
//        int[] ints = {1, 7, 3, 6, 5, 6};
        int[] ints = {-1, -1, 0, 0, -1, -1};
        int result = pivotIndex(ints);
    }

    public int pivotIndex(int[] nums) {

        Map<Integer, Integer> indexToSumLeftToRight = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer integer = indexToSumLeftToRight.get(i - 1);
            if (integer != null) {
                indexToSumLeftToRight.put(i, integer + nums[i]);
            } else {
                indexToSumLeftToRight.put(i, nums[i]);
            }
        }

        Map<Integer, Integer> indexToSum1 = new HashMap<>();
        for (int i = nums.length - 1; i >= 0; i--) {
            Integer integer = indexToSum1.get(i + 1);
            if (integer != null) {
                indexToSum1.put(i, integer + nums[i]);
            } else {
                indexToSum1.put(i, nums[i]);
            }
        }

//        If there are multiple pivot indexes, you should return the left-most pivot index.
        for (int i = 0; i < nums.length; i++) {
            if (indexToSumLeftToRight.get(i).equals(indexToSum1.get(i)))
                return i;
        }

        return -1;
    }

    @Test
    public void earnixTest() {
//  Write methods to implement the multiply, subtract, and divide operations for integers.
//  The results of all of these are integers. Use only the add method
        add(-5, 3);
        int multiply = multiply(5, -3);
        int subtract = subtract(5, 3);
    }

    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return add(a, negate(b));
    }

    public int multiply(int a, int b) {
        int result = 0;
        int iterationNum = 0;
        if (b > 0) {
            while (iterationNum < b) {
                iterationNum = add(iterationNum, 1);
                result = add(a, result);
            }
        } else {
            while (iterationNum > b) {
                iterationNum = add(iterationNum, -1);
                result = add(-a, result);
            }
        }

        return result;
    }

    public int negate(int value) {//TODO fix
        int iterationNum = 0;
        int breakNum = 0;
        if (value > iterationNum) {
            while (breakNum >= 0) {
                iterationNum = add(iterationNum, -1);
                breakNum = add(iterationNum, value);
            }
            return add(iterationNum, breakNum);
        }
        return value;
    }


    @Test
    public void ATATTest() {
        String s1 = "9384";
        assertEquals(4, solve(s1));
        String s2 = "138925";
        assertEquals(4, solve(s2));
    }

    private int solve(String s) {

        Map<Integer, Integer> indexToSumMap = new HashMap<>();
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            String digitAsStr = s.substring(i, i + 1);
            Integer digitValue = Integer.valueOf(digitAsStr);
            values.add(digitValue);
            indexToSumMap.put(i, digitValue);
        }

        List<Integer> prefixSun = new ArrayList<>();
        prefixSun.add(values.get(0));
        for (int i = 1; i < values.size() / 2; i++) {
            Integer sum = values.get(i - 1);
            prefixSun.add(values.get(i) + sum);
        }

        List<Integer> suffixSun = new ArrayList<>();
        suffixSun.add(values.get(values.size() - 1));
        for (int i = values.size() - 2; i >= values.size() / 2; i--) {
            Integer sum = values.get(i + 1);
            suffixSun.add(values.get(i) + sum);
        }


        for (int i = 0; i < values.size() - 1; i++) {
            Integer prefixSunValue = prefixSun.get(i);
            Integer suffixSunValue = suffixSun.get(i);
            if (prefixSunValue.equals(suffixSunValue)) {
                return (i + 1) * 2;
            }

        }

        return -1;
    }

    @Test
    public void splitAnArrayIntoTwoEqualSumSubArrays() {
        /**
         *        https://www.geeksforgeeks.org/split-array-two-equal-sum-subarrays/
         */
        List<Integer> list = Arrays.asList(3, 2, 5);
        boolean result = splitAnArrayIntoTwoEqualSumSubArrays(list);
        System.out.print("result: " + result);
    }

    public static boolean splitAnArrayIntoTwoEqualSumSubArrays(List<Integer> integers) {
        int leftSum = 0;
        int rightSum = integers.stream().reduce(0, Integer::sum);

        for (Integer integer : integers) {
            leftSum += integer;
            rightSum -= integer;
            if (leftSum == rightSum)
                return true;
        }
        return false;
    }

    @Test
    public void findIfArrayCanBeDividedIntoTwoSubarraysOfEqualSum() {
        /**
         *       https://www.geeksforgeeks.org/find-if-array-can-be-divided-into-two-subarrays-of-equal-sum/
         */
        assertTrue(findIfArrayCanBeDividedIntoTwoSubArraysOfEqualSum(new int[]{6, 2, 3, 2, 1}));
        assertTrue(findIfArrayCanBeDividedIntoTwoSubArraysOfEqualSum(new int[]{6, 1, 3, 2, 5}));
        assertTrue(findIfArrayCanBeDividedIntoTwoSubArraysOfEqualSum(new int[]{6, -2, -3, 2, 3}));
        assertFalse(findIfArrayCanBeDividedIntoTwoSubArraysOfEqualSum(new int[]{6, -2, 3, 2, 3}));
    }

    private boolean findIfArrayCanBeDividedIntoTwoSubArraysOfEqualSum(int[] arr) {

        int leftSum = 0;
        int rightSum = Arrays.stream(arr).boxed().reduce(0, Integer::sum);

        for (int i = 0; i < arr.length; i++) {
            if (i > 0) {
                leftSum += arr[i - 1];
            }
            rightSum -= arr[i];
            if (leftSum == rightSum)
                return true;
        }
        return false;
    }


    @Test
    public void numberOfElementsInIntersection() {
        /**
         *  https://practice.geeksforgeeks.org/problems/intersection-of-two-arrays2404/1
         */
        assertEquals(1, numberOfElementsInIntersection(new int[]{89, 2, 4}, new int[]{89, 24, 75, 11, 23}));
        assertEquals(4, numberOfElementsInIntersection(new int[]{1, 2, 3, 4, 5, 6}, new int[]{3, 4, 5, 6, 7}));
    }

    private int numberOfElementsInIntersection(int[] arr1, int[] arr2) {
        Set<Integer> set = new HashSet<>();
        set.addAll(Arrays.stream(arr1).boxed().collect(Collectors.toSet()));

        int result = 0;
        for (Integer integer : arr2) {
            if (set.contains(integer))
                result++;
        }
        return result;
    }

    @Test
    public void removeObjFromListTest() {
        List<Integer> integerList = Arrays.asList(1, 3, 5, 7).stream().collect(Collectors.toList());
        integerList.remove(1);
        integerList.remove(new Integer(7));
        System.out.println(integerList);
    }

    @Test
    public void findCommonElementsInMultipleLists() {
        /**
         * https://stackoverflow.com/questions/15183982/is-there-a-way-to-find-common-elements-in-multiple-lists
         */
//        List<List<Integer>> lists = new ArrayList<>();
//        lists.add(Arrays.asList(1, 3, 5));
//        lists.add(Arrays.asList(1, 6, 7, 9, 3));
//        lists.add(Arrays.asList(1, 3, 10, 11));

        Set<Integer> intersection = intersection(Arrays.asList(1, 3, 5), Arrays.asList(1, 6, 7, 9, 3), Arrays.asList(1, 3, 10, 11));
        assertEquals(Sets.newHashSet(1, 3), intersection);

        Set<Integer> set = retainAll(Sets.newHashSet(1, 3, 5), Sets.newHashSet(1, 6, 7, 9, 3), Sets.newHashSet(1, 3, 10, 11));
        assertEquals(Sets.newHashSet(1, 3), set);
    }

    public <T> Set<T> intersection(List<T>... list) {
        Set<T> result = Sets.newHashSet(list[0]);
        for (List<T> numbers : list) {
            result = Sets.intersection(result, Sets.newHashSet(numbers));
        }
        return result;
    }


    public <T> Set<T> retainAll(Set<T>... sets) {
        Set<T> result = sets[0];
        for (Set<T> set : sets) {
            result.retainAll(set);
        }
        return result;
    }

    @Test
    public void reverseListTest() {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);
        printList(head);
        ListNode listNode = reverseList(head);
        printList(listNode);
        ListNode listNode1 = reverseListUsingStack(listNode);
        printList(listNode1);

    }

    static void printList(ListNode temp) {
        while (temp != null) {
            System.out.print(temp.val + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        ListNode next = null;
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        head = prev;
        return head;
    }

    public ListNode reverseListUsingStack(ListNode head) {
        if (head == null || head.next == null)
            return head;

        Stack<ListNode> stk = new Stack<>();

        // Push the elements of list to stack
        ListNode ptr = head;
        while (ptr.next != null) {
            stk.push(ptr);
            ptr = ptr.next;
        }
        head = ptr;
        while (!stk.isEmpty()) {
            ptr.next = stk.pop();
            ptr = ptr.next;
        }
//        ptr.next = null;
        return head;
    }

    @ToString
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

    }

    @Test
    public void distributedTest() {
        int numOfInstances = 2;
//        int numOfInstances = 5;

        String value1 = "agf";
        String value2 = "b";
        String value3 = "cffffb";
        String value4 = "cffffffb";

        int value1Hash = Math.abs(value1.hashCode());
        int value2Hash = Math.abs(value2.hashCode());
        int value3Hash = Math.abs(value3.hashCode());
        int value4Hash = Math.abs(value4.hashCode());

        int i1 = value1Hash % numOfInstances;
        int i2 = value2Hash % numOfInstances;
        int i3 = value3Hash % numOfInstances;
        int i4 = value4Hash % numOfInstances;


    }
}
