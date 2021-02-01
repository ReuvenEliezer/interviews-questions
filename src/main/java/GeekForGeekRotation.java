import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class GeekForGeekRotation {
    /**
     * https://practice.geeksforgeeks.org/problems/rotate-array-by-n-elements/0
     *
     * @param args
     */
    public static void main(String[] args) {
        doRotate();
    }

    private static void doRotate() {
//        Scanner scan = new Scanner(System.in);
//        String testCaseNumString = scan.nextLine();

        String testCaseNumString = "2";
        int testCaseNum = Integer.parseInt(testCaseNumString);
        int elementsListIndex = 2;
        int sizeAndRotationIndex = 1;
        int loop = 1 + testCaseNum * elementsListIndex;
        for (int i = 0; i < loop - elementsListIndex; i++, sizeAndRotationIndex++, elementsListIndex++) {
            Integer rotationNum = Integer.valueOf("5 2".split(" ")[1]);
            List<Integer> elements = Arrays.stream("1 2 3 4 5".split(" ")).map(value -> Integer.parseInt(value)).collect(Collectors.toList());
//            Integer rotationNum = Integer.valueOf(scan.nextLine().split(" ")[1]);
//            List<Integer> elements = Arrays.stream(scan.nextLine().split(" ")).map(value -> Integer.parseInt(value)).collect(Collectors.toList());
            int[] arr = elements.stream().mapToInt(Integer::intValue).toArray();
            int[] result = rotateArray(-rotationNum, arr);
            for (int j : result)
                System.out.print(j + " ");
            System.out.println();
        }
    }

    @Test
    public void test() {
        Scanner scan = new Scanner(System.in);
//        String testCaseNumString = scan.nextLine();
        String testCaseNumString = "2";
        int testCaseNum = Integer.parseInt(testCaseNumString);
//        int totalColumnsIndex  = 1;
        int elementsListIndex1 = 2;
        int elementsListIndex2 = 3;
        int loop = 1 + testCaseNum * elementsListIndex2;
        for (int i = 0; i < loop - elementsListIndex2; i++,
//                totalColumnsIndex ++,
                elementsListIndex1++, elementsListIndex2++) {

            int elements1 = Arrays.stream("8 4 5 6 7".split(" ")).map(value -> Integer.parseInt(value)).mapToInt(Integer::intValue).sum();
            int elements2 = Arrays.stream("2 3 4 5 6 7".split(" ")).map(value -> Integer.parseInt(value)).mapToInt(Integer::intValue).sum();

            if (elements1 > elements2) {
                System.out.println("C1");
            } else {
                System.out.println("C2");
            }
        }
    }

    @Test
    public void test1() {
        /**
         * https://practice.geeksforgeeks.org/contest-problem/sahil-loves-gfg/0/
         */
        Scanner scan = new Scanner(System.in);
//        String testCaseNumString = scan.nextLine();
        String testCaseNumString = "2";
        int testCaseNum = Integer.parseInt(testCaseNumString);
        int totalStrIndex = 1;
        int loop = 1 + testCaseNum;
        String strToSearch = "gfg";
        String data = "geefgfgksforgfgeeks";

        for (int i = 0; i < loop - totalStrIndex; i++) {
            int totalFoundChars = data.length() - data.replaceAll(strToSearch, "").length();
           int count =  totalFoundChars / strToSearch.length();
            int count1 = StringUtils.countMatches(data, strToSearch);
            if (count > 0) {
                System.out.println(count);
            } else {
                System.out.println(-1);
            }
        }
    }


    static int[] rotateArray(int n, int[] data) {
        if (n < 0) // rotating left?
        {
            n = -n % data.length; // convert to +ve number specifying how
            // many positions left to rotate & mod
            n = data.length - n;  // rotate left by n = rotate right by length - n
        }
        int[] result = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            result[(i + n) % data.length] = data[i];
        }
        return result;
    }


    @Test
    public void findKRotationTest() {
        /**
         * https://practice.geeksforgeeks.org/problems/rotation4723/1
         Given an ascending sorted rotated array Arr of distinct integers of size N. The array is right rotated K times. Find the value of K.

         Example 1:

         Input:
         N = 5
         Arr[] = {5, 1, 2, 3, 4}
         Output: 1
         Explanation: The given array is 5 1 2 3 4.
         The original sorted array is 1 2 3 4 5.
         We can see that the array was rotated
         1 times to the right.
         Example 2:

         Input:
         N = 5
         Arr[] = {1, 2, 3, 4, 5}
         Output: 0
         Explanation: The given array is not rotated.
         Your Task:
         Complete the function findKRotation() which takes array arr and size n, as input parameters and returns an integer representing the answer. You don't to print answer or
         */

        int arr1[] = {5, 1, 2, 3, 4};
        Assert.assertEquals(1, findKRotation(arr1, arr1.length));
        int arr2[] = {1, 2, 3, 4, 5};
        Assert.assertEquals(0, findKRotation(arr2, arr2.length));

    }

    int findKRotation(int[] arr, int arrSize) {
        if (arr.length < 2) return 0;
        int min = Arrays.stream(arr).min().getAsInt();

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == min)
                return i;
        }
        return -1;
    }

    @Test
    public void rightRotation() {
        int[] array = new int[]{1, 2, 3, 4, 5};
        int numberOfRotation = 12;
        for (int i : array)
            System.out.print(i + " ");

        System.out.println();

        int laseCell = array.length;
        numberOfRotation %= array.length;
        for (int j = 0; j < numberOfRotation; j++) {
            for (int i = array.length - 1; i >= 0; i--) {
                if (i == array.length - 1) {
                    laseCell = array[i];
                }
                if (i - 1 >= 0)
                    array[i] = array[i - 1];
                else
                    array[i] = laseCell;
            }
        }
        for (int i : array)
            System.out.print(i + " ");

    }

    public void rotate(int[] nums, int k) {
        // speed up the rotation
        k %= nums.length;//optimization
        int temp, previous;
        for (int i = 0; i < k; i++) {
            previous = nums[nums.length - 1];
            for (int j = 0; j < nums.length; j++) {
                temp = nums[j];
                nums[j] = previous;
                previous = temp;
            }
        }
    }

    @Test
    public void RightRotation() {
        int[] array = new int[]{1, 2, 3, 4, 5};

        int numberOfRotation = 1;
//        int numberOfRotation = -1;

        System.out.println("Before " + Arrays.toString(array));

        int[] resultArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            resultArray[(i + numberOfRotation) % array.length] = array[i];
        }

        //left rotation
//        for(int i = 0; i < array.length; i++){
//            resultArray[(i+(array.length-numberOfRotation)) % array.length ] = array[i];
//        }


//        for (int i : resultArray)
//            System.out.print(i + " ");
//
//        System.out.println();

        List<Integer> collect = Arrays.stream(array).boxed().collect(Collectors.toList());
        Collections.rotate(collect, numberOfRotation);
        array = collect.stream().mapToInt(Integer::intValue).toArray();
        System.out.println("After " + Arrays.toString(array));
    }

    @Test
    public void leftRotation() {
//        https://practice.geeksforgeeks.org/problems/rotate-array-by-n-elements/0/?track=interview-arrays&batchId=117#
        String[] args = {"2", "5 2", "1 2 3 4 5", "10 3", "2 4 6 8 10 12 14 16 18 20"};
        if (args == null || args.length < 3) return;
        int totalIterationTestCases = Integer.valueOf(args[0]);
        int elementsListIndex = 2;
        int sizeAndRotationIndex = 1;
        for (int i = 0; i < totalIterationTestCases; i++, sizeAndRotationIndex++, elementsListIndex++) {
            String sizeAndRotationNumStr = args[i + sizeAndRotationIndex];

            Integer arrSize = Integer.valueOf(sizeAndRotationNumStr.split(" ")[0]);
            int[] arr = new int[arrSize];
            Integer rotationNum = Integer.valueOf(sizeAndRotationNumStr.split(" ")[1]);
            String elementsStr = args[i + elementsListIndex];
            String[] elementsArr = elementsStr.split(" ");
            for (int j = 0; j < elementsArr.length; j++) {
                Integer element = Integer.valueOf(elementsArr[j]);
                arr[j] = element;
            }

            int firstCell = 0;
            rotationNum %= arr.length;
            for (int j = 0; j < rotationNum; j++) {
                for (int a = 0; a < arr.length; a++) {
                    if (a == 0) {
                        firstCell = arr[a];
                    }
                    if (a + 1 < arr.length)
                        arr[a] = arr[a + 1];
                    else
                        arr[a] = firstCell;
                }
            }
            for (int j : arr)
                System.out.print(j + " ");
            System.out.println();
        }

    }

    class RotationParams {
        List<Integer> elements;
        int rotationNum;

        public RotationParams(List<Integer> elements, int rotationNum) {
            this.elements = elements;
            this.rotationNum = rotationNum;
        }
    }

    @Test
    @Ignore
    public void leftRotation1() {
//        https://practice.geeksforgeeks.org/problems/rotate-array-by-n-elements/0/?track=interview-arrays&batchId=117#

        Scanner scan = new Scanner(System.in);
        String testCaseNumString = scan.nextLine();
//        String testCaseNumString = "2";
        int testCaseNum = Integer.parseInt(testCaseNumString);
        Map<Integer, RotationParams> rotationParamsMap = new TreeMap<>();
        int loop = 1 + testCaseNum * 2;
        int elementsListIndex1 = 2;
        int sizeAndRotationIndex1 = 1;
        for (int i = 0; i < loop - elementsListIndex1; i++, sizeAndRotationIndex1++, elementsListIndex1++) {
//            Integer rotationNum = Integer.valueOf("5 2".split(" ")[1]);
//            List<Integer> elementsArr = Arrays.stream("1 2 3 4 5".split(" ")).map(value -> Integer.parseInt(value)).collect(Collectors.toList());
            Integer rotationNum = Integer.valueOf(scan.nextLine().split(" ")[1]);
            List<Integer> elementsArr = Arrays.stream(scan.nextLine().split(" ")).map(value -> Integer.parseInt(value)).collect(Collectors.toList());
            RotationParams rotationParams = new RotationParams(elementsArr, rotationNum);
            rotationParamsMap.put(i, rotationParams);
        }

        for (Map.Entry<Integer, RotationParams> entry : rotationParamsMap.entrySet()) {
            RotationParams rotationParams = entry.getValue();
            List<Integer> elements = rotationParams.elements;
            int[] arr = elements.stream().mapToInt(Integer::intValue).toArray();
            int rotationNum = rotationParams.rotationNum;


            int firstCell = 0;
            rotationNum %= arr.length;
            for (int j = 0; j < rotationNum; j++) {
                for (int a = 0; a < arr.length; a++) {
                    if (a == 0) {
                        firstCell = arr[a];
                    }
                    if (a + 1 < arr.length)
                        arr[a] = arr[a + 1];
                    else
                        arr[a] = firstCell;
                }
            }
            for (int j : arr)
                System.out.print(j + " ");
            System.out.println();
        }

    }

    @Test
    public void LeftRotation() {
        int[] array = new int[]{1, 2, 3, 4, 5};
        int numberOfRotation = 12;
//        rotate(array,16);


        int[] resultArray = new int[array.length];
//        for (int i = 0; i < array.length; i++)
//            resultArray[(i + (array.length - numberOfRotation)) % array.length] = array[i];

        for (int i : array)
            System.out.print(i + " ");

        System.out.println();

        int firstCell = 0;
        numberOfRotation %= array.length;
        for (int j = 0; j < numberOfRotation; j++) {
            for (int i = 0; i < array.length; i++) {
                if (i == 0) {
                    firstCell = array[i];
                }
                if (i + 1 < array.length)
                    array[i] = array[i + 1];
                else
                    array[i] = firstCell;
            }
        }
        for (int i : array)
            System.out.print(i + " ");

    }
}


