import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

public class Zesty {

    /**
     * From Zesty to Everyone 08:35 PM
     * A numeric array of length N is given. We need to design a function that finds all positive numbers
     * in the array that have their opposites in it as well.
     * Describe approaches for solving optimal worst case and optimal average case performance, respectively.
     * <p>
     * -7 4 -3 2 2 -8 -2 3 3 7 -2 3 -2
     * ->
     * 7,3,2,2
     */
    @Test
    public void test() {
        int[] ints = new int[]{-7, 4, -3, 2, 2, -8, -2, 3, 3, 7, -2, 3, -2};
        Assert.assertEquals(Arrays.asList(7, 3, 2, 2).stream().sorted().collect(Collectors.toList()),
                calcResult(ints).stream().sorted().collect(Collectors.toList()));

    }

    private List<Integer> calcResult(int[] arr) {
        List<Integer> result = new ArrayList<>();

        //collect all negative numbers in arr and put it to map
        Map<Integer, Integer> negativeValueToInstanceMap = new HashMap<>();
        for (Integer integer : arr) {
            if (integer < 0) {
                negativeValueToInstanceMap.merge(integer, 1, Integer::sum);
//                Integer instanceNum = negativeValueToInstanceMap.get(integer);
//                if (instanceNum == null) {
//                    negativeValueToInstanceMap.put(integer, 1);
//                } else {
//                    negativeValueToInstanceMap.put(integer, ++instanceNum);
//                }
            }
        }

        //find the positive number in the map (by -) and if found -> reduce the instance from negative map.
        for (Integer integer : arr) {
            if (integer > 0) {
                Integer instanceNum = negativeValueToInstanceMap.computeIfPresent(-integer, (a, b) -> b - 1);
                if (instanceNum != null && instanceNum >= 0) {
                    result.add(integer);
                }
//                Integer instanceNum = negativeValueToInstanceMap.get(-integer);
//                if (instanceNum != null) {
//                    result.add(integer);
//                    if (instanceNum == 1) {
//                        negativeValueToInstanceMap.remove(-integer);
//                    } else {
//                        negativeValueToInstanceMap.put(-integer, --instanceNum);
//                    }
//                }
            }
        }
        return result;
    }


    @Test
    public void mazeSolverBfsAndDfsTest() {
        // 1 mean wall (no way to pass), 0 mean - can to move via this point
        int[][] maze = {
                {1, 1, 0, 1},
                {1, 0, 0, 0},
                {1, 0, 0, 0}
        };

        Coordinate startPoint = new Coordinate(2, 0);
        Coordinate endPoint = new Coordinate(3, 1);
        boolean isSolvable = isSolvableByBfsAlgorithm(maze, startPoint, endPoint);
        assertThat(isSolvable).isTrue();

        assertThat(isSolvableByBfsAlgorithm(maze, startPoint, startPoint)).isTrue();

        List<Coordinate> pointsPath = calcFullPathPointsByDfsAlgorithm(maze, startPoint, new Coordinate(1, 2));

        int[][] maze1 = {
                {1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1},
                {1, 0, 1, 0, 1},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 0, 1},
                {1, 0, 1, 1, 1}
        };
        Coordinate startPoint1 = new Coordinate(1, 2);
        Coordinate endPoint1 = new Coordinate(3, 4);
        assertThat(isSolvableByBfsAlgorithm(maze1, startPoint1, endPoint1)).isTrue();
        assertThat(isSolvableByBfsAlgorithm(maze1, new Coordinate(1, 2), new Coordinate(1, 5))).isFalse();


        List<Coordinate> pointsPath1 = calcFullPathPointsByDfsAlgorithm(maze1, startPoint1, endPoint1);
    }

    private List<Coordinate> calcFullPathPointsByDfsAlgorithm(int[][] maze, Coordinate startPoint, Coordinate endPoint) {
        validate(maze, startPoint, endPoint);

        if (startPoint.equals(endPoint)) {
            return Lists.newArrayList(startPoint, endPoint);
        }

        Stack<Coordinate> stack = new Stack<>();
        stack.push(startPoint);

        Set<Coordinate> coordinatesVisited = new HashSet<>();

        Map<Coordinate, Coordinate> childToParentMap = new HashMap<>();
        while (!stack.isEmpty()) {
            Coordinate currentCoordinate = stack.pop();

            if (currentCoordinate.equals(endPoint)) {
                break; //the end point is reached
            }

            List<Coordinate> nonVisitedNeighbors = getPossibleNeighbors(maze, currentCoordinate).stream()
                    .filter(neighbor -> !coordinatesVisited.contains(neighbor))
                    .collect(Collectors.toList());

            nonVisitedNeighbors.forEach(e -> childToParentMap.put(e, currentCoordinate));

            coordinatesVisited.add(currentCoordinate);

            stack.addAll(nonVisitedNeighbors);
        }

        List<Coordinate> coordinatesPath = new ArrayList<>();
        coordinatesPath.add(endPoint);
        Coordinate current = endPoint;
        while (!current.equals(startPoint)){
            current = childToParentMap.get(current);
            coordinatesPath.add(current);
        }

        Collections.reverse(coordinatesPath);
        return coordinatesPath;

    }

    private boolean isSolvableByBfsAlgorithm(int[][] maze, Coordinate startCoordinate, Coordinate endCoordinate) {
        validate(maze, startCoordinate, endCoordinate);

//        if (startCoordinate.equals(endCoordinate)) {
//            return true;
//        }

        Queue<Coordinate> coordinateQueue = new ArrayDeque<>();
        coordinateQueue.add(startCoordinate);

        Set<Coordinate> alreadyCheckedSet = new HashSet<>(); //in order to avoid a circular passing

        while (!coordinateQueue.isEmpty()) {
            Coordinate coordinate = coordinateQueue.poll();
            if (coordinate.equals(endCoordinate)) {
                return true;
            }
            List<Coordinate> neighborCoordinateList = getPossibleNeighbors(maze, coordinate);

            //add all neighborCoordinateList that not already checked into the queue
            neighborCoordinateList.stream()
                    .filter(neighborCoordinate -> !alreadyCheckedSet.contains(neighborCoordinate))
                    .forEach(coordinateQueue::add);

            alreadyCheckedSet.add(coordinate);
        }
        return false;
    }

    private static void validate(int[][] maze, Coordinate startCoordinate, Coordinate endCoordinate) {
        int rowsNum = maze.length; // Number of rows
        int colsNum = maze[0].length; // Number of columns (assuming all rows have the same length)

        if (startCoordinate.xLocation < 0) {
            throw new IllegalArgumentException(String.format("startCoordinate.xLocation %s must be a positive number", startCoordinate.xLocation));
        }

        if (startCoordinate.yLocation < 0) {
            throw new IllegalArgumentException(String.format("startCoordinate.yLocation %s must be a positive number", startCoordinate.yLocation));
        }

        if (endCoordinate.xLocation < 0) {
            throw new IllegalArgumentException(String.format("endCoordinate.xLocation %s must be a positive number", startCoordinate.xLocation));
        }

        if (endCoordinate.yLocation < 0) {
            throw new IllegalArgumentException(String.format("endCoordinate.yLocation %s must be a positive number", startCoordinate.yLocation));
        }

        if (startCoordinate.xLocation > colsNum) {
            throw new IllegalArgumentException(String.format("startCoordinate.xLocation %s bigger than colsNum=%s", startCoordinate.xLocation, colsNum));
        }

        if (endCoordinate.xLocation > colsNum) {
            throw new IllegalArgumentException(String.format("endCoordinate.xLocation %s bigger than colsNum=%s", endCoordinate.xLocation, colsNum));
        }

        if (startCoordinate.yLocation > rowsNum) {
            throw new IllegalArgumentException(String.format("startCoordinate.yLocation %s bigger than rowsNum=%s", startCoordinate.yLocation, rowsNum));
        }

        if (endCoordinate.yLocation > rowsNum) {
            throw new IllegalArgumentException(String.format("endCoordinate.yLocation %s bigger than rowsNum=%s", endCoordinate.yLocation, rowsNum));
        }

        if (maze[startCoordinate.yLocation][startCoordinate.xLocation] == 1) {
            throw new IllegalArgumentException(String.format("startCoordinate %s is in a wall Coordinate", startCoordinate));
        }

        if (maze[endCoordinate.yLocation][endCoordinate.xLocation] == 1) {
            throw new IllegalArgumentException(String.format("endCoordinate %s is in a wall Coordinate", endCoordinate));
        }

    }

    private List<Coordinate> getPossibleNeighbors(int[][] maze, Coordinate coordinate) {
        List<Coordinate> neighbors = new ArrayList<>();
        int rowsNum = maze.length; // Number of rows
        int colsNum = maze[0].length; // Number of columns (assuming all rows have the same length)

        //add left neighbor
        if (coordinate.xLocation - 1 > 0 && maze[coordinate.yLocation][coordinate.xLocation - 1] == 0) {
            neighbors.add(new Coordinate(coordinate.xLocation() - 1, coordinate.yLocation));
        }

        //add right neighbor
        if (coordinate.xLocation + 1 < colsNum && maze[coordinate.yLocation][coordinate.xLocation + 1] == 0) {
            neighbors.add(new Coordinate(coordinate.xLocation() + 1, coordinate.yLocation));
        }

        //add up neighbor
        if (coordinate.yLocation - 1 > 0 && maze[coordinate.yLocation - 1][coordinate.xLocation] == 0) {
            neighbors.add(new Coordinate(coordinate.xLocation(), coordinate.yLocation - 1));
        }

        //add down neighbor
        if (coordinate.yLocation + 1 < rowsNum && maze[coordinate.yLocation + 1][coordinate.xLocation] == 0) {
            neighbors.add(new Coordinate(coordinate.xLocation(), coordinate.yLocation + 1));
        }
        return neighbors;
    }

    private record Coordinate(int xLocation, int yLocation) {
    }

}
