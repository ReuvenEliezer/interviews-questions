import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MergeOverlappingIntervals {
    /**
     * https://practice.geeksforgeeks.org/problems/overlapping-intervals4919/1#editorial
     */
    @Test
    public void test() {
        ArrayList<Pair> arr = new ArrayList<>();
        arr.add(new Pair(1, 3));
        arr.add(new Pair(2, 4));
        arr.add(new Pair(6, 8));
        arr.add(new Pair(9, 10));
        ArrayList<Pair> pairs = overlappedInterval(arr);
    }

    public static ArrayList<Pair> overlappedInterval(ArrayList<Pair> pairList) {
        if (pairList.size() < 2)
            return pairList;

        pairList.sort(Comparator.comparing(Pair::getFirst));
        Collections.sort(pairList, Comparator.comparing(Pair::getFirst));

        pairList.sort((i1, i2) -> i1.first - i2.first);
        pairList.sort(new Comparator<Pair>() {
            @Override
            public int compare(Pair i1, Pair i2) {
                return i1.first - i2.first;
            }
        });
        ArrayList<Pair> result = new ArrayList<>();
        for (Pair pair : pairList) {
            if (result.isEmpty()) {
                result.add(pair);
            } else {
                // if current interval is not overlapping with stack top,
                // push it to the stack
                Pair pair1 = result.get(result.size() - 1);
                if (pair1.second < pair.first)
                    result.add(pair);

                    // Otherwise update the ending time of top if ending of current
                    // interval is more
                else if (pair1.second < pair.second) {
                    pair1.second = pair.second;
                }
            }
        }
        return result;
    }

    private class Pair {
        int first;
        int second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        public int getFirst() {
            return first;
        }

        public int getSecond() {
            return second;
        }
    }
}
