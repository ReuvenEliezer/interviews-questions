import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class Varonis {

    @Test
    public void isPairSumExists() {
        Assert.assertTrue(findPairSum(new int[]{-1, 7, 3, 9}, 6));
        Assert.assertTrue(findPairSum(new int[]{-1, 7, 3, 9}, 16));
        Assert.assertTrue(findPairSum(new int[]{-1, 7, 3, 9}, 10));
        Assert.assertFalse(findPairSum(new int[]{-1, 7, 3, 9}, 11));
    }

    @Test
    public void isArrContainsOnlyOneLowestPoint() {
        //בהינתן נקודת מיני' אחת (פרבולה שמחה) - מצא את נקודת המיני'
        Assert.assertTrue(search(new int[]{8, 5, 4, 3, 1, 2, 6, 9}));
        Assert.assertFalse(search(new int[]{1, 2, 3, 4, 5}));
        Assert.assertFalse(search(new int[]{1, 1, 1, 1}));
    }


    private boolean search(int[] arr) {
        if (arr.length < 3)
            return false;

        int middle = arr.length / 2;
        if (arr[middle - 1] > arr[middle] && arr[middle + 1] > arr[middle])
            return true;

        int[] leftArr = new int[middle];

        for (int i = 0; i < middle; i++) {
            leftArr[i] = arr[i];
        }

        if (leftArr[leftArr.length - 1] < arr[middle])
            return search(leftArr);

        int[] rightArr = new int[middle];
        for (int i = middle; i < arr.length; i++) {
            rightArr[i - middle] = arr[i];
        }
//        if (rightArr[rightArr.length - 1] < arr[middle])
        return search(rightArr);
    }


    private boolean findPairSum(int[] arr, int sum) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            if (set.contains(sum - arr[i]))
                return true;
            set.add(arr[i]);
        }
        return false;
    }


}
