import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Stream;

public class InterviewsTest {

    @Test
    public void ZoomIntoTest() {
//        נתון מערך לדוג':
//        אם תוצאת הסכום של 2 אברים במערך קיימת במערך - יש להדפיסה.
//                לדוג' 1+3 = 4
//        4 קיים במערך.
        int[] arr = {1, 3, 4, 8, 2, 6, 10, 7};
        // printSumOfPairIfExistInArr(arr);
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
                        integersPrinted.put(sum, true);
                        System.out.println(first + " " + second + " = " + sum);
                    }
                }
                i1++;
            }
        }

        System.out.println(i1);
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

//        String s = "WeTestCodErs";
        String s = "Tt";

        String result = solution(s);
        System.out.println("result: " + result);
    }

    // find max letter that appears both in upper and lower case
    public String solution(String str) {
//        HashSet<Character> characterHashSet = new HashSet<>();
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

        HashSet<Integer> characterHashSet = new HashSet<>();
        for (int ch : str.toCharArray())
            characterHashSet.add(ch);
        int diffFromUpperToLowerChar = 'z' - 'Z';
        // From Z to A
        for (int upperCaseIndex = 'Z'; upperCaseIndex >= 'A'; upperCaseIndex--) {
            if (characterHashSet.contains(upperCaseIndex)) {
                int lowerCase = upperCaseIndex + diffFromUpperToLowerChar;
                if (characterHashSet.contains(lowerCase))
                    return String.valueOf(upperCaseIndex);
            }
        }
        return "NO";
    }

}
