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
        ArrayList<pair> arr = new ArrayList<>();
        arr.add(new pair(1, 3));
        arr.add(new pair(2, 4));
        arr.add(new pair(6, 8));
        arr.add(new pair(9, 10));
        ArrayList<pair> pairs = overlappedInterval(arr);
    }

    public static ArrayList<pair> overlappedInterval(ArrayList<pair> pairList) {
        if (pairList.size() < 2)
            return pairList;

        pairList.sort(Comparator.comparing(pair::getFirst));
        Collections.sort(pairList, Comparator.comparing(pair::getFirst));

        pairList.sort((i1, i2) -> i1.first - i2.first);
        pairList.sort(new Comparator<pair>() {
            @Override
            public int compare(pair i1, pair i2) {
                return i1.first - i2.first;
            }
        });
        ArrayList<pair> result = new ArrayList<>();
        for (pair pair : pairList) {
            if (result.isEmpty()) {
                result.add(pair);
            } else {
                // if current interval is not overlapping with stack top,
                // push it to the stack
                pair pair1 = result.get(result.size() - 1);
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

    private class pair {
        int first;
        int second;

        public pair(int first, int second) {
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
