
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class NiceTest {

    /**
     * Given an array of 2 integer arrays representing x,y coordinates, write a function to calculate the S amount of closest points to origin (0,0).
     * <p>
     * For example: [[1,2], [3,5], [-1,1], [7,9]], if we would want to get the 2 closest points to origin the output would be: [1,2], [-1,1]
     */

    @Test
    public void test() {
        double[][] arr = new double[][]{{1, 2}, {3, 5}, {-1, 1}, {7, 9}};
        List<Coordinates> result = findRange(arr, 2, 0);
        assertThat(result).containsExactlyInAnyOrderElementsOf(Lists.newArrayList(new Coordinates(1, 2), new Coordinates(-1, 1)));
    }

    private List<Coordinates> findRange(double[][] arr, int nums, double calcFrom) {
        if (nums >= arr.length) {
            return Arrays.stream(arr)
                    .map(pairValues -> new Coordinates(pairValues[0], pairValues[1]))
                    .toList();
        }

        List<Result> results = new ArrayList<>();
//        double[][] resultArr = new double[arr.length][2];
        for (int i = 0; i < arr.length; i++) {
            double[] ints = arr[i];
            Result result = calcDistance(ints, calcFrom);
//            resultArr[i][0] = result.getCoordinates().getX();
//            resultArr[i][1] = result.getCoordinates().getY();
            results.add(result);
        }

        return results.stream()
                .sorted(Comparator.comparing(Result::distance))
                .map(Result::coordinates)
                .limit(nums)
                .toList();
    }

    private static Result calcDistance(double[] arr, double calcFrom) {
        double distanceX = Math.abs(arr[0]) - calcFrom;
        double distanceY = Math.abs(arr[1]) - calcFrom;
        return new Result(new Coordinates(arr[0], arr[1]), distanceX + distanceY);
    }

    record Result(Coordinates coordinates, double distance) {
    }

    record Coordinates(double x, double y) {
    }

}
