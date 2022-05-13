import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class LinkedListTest {

    @Test
    public void reverseLastElementToFirstTest() {
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5, 6);
        System.out.println(integerList);
        List<Integer> reverse = reverseLastElementToFirst(integerList);
        System.out.println(reverse);
        List<Integer> reverse2 = reverseLastElementToFirst(reverse);
        System.out.println(reverse2);

        /**
         * [1, 2, 3, 4, 5, 6]
         * [6, 1, 2, 3, 4, 5]
         * [5, 6, 1, 2, 3, 4]
         */
    }

    private List<Integer> reverseLastElementToFirst(List<Integer> integerList) {
        List<Integer> result = new ArrayList<>();
        result.add(integerList.get(integerList.size() - 1));
        for (int i = 0; i < integerList.size() - 1; i++) {
            result.add(integerList.get(i));
        }
        return result;
    }

    @Test
    public void reverseLastElementToFirstTest1() {
        LinkedList<Integer> integerList = new LinkedList<>();
        integerList.addAll(Arrays.asList(1, 2, 3, 4, 5, 6));
        System.out.println(integerList);
        reverseLastElementToFirst1(integerList);
        System.out.println(integerList);
        reverseLastElementToFirst1(integerList);
        System.out.println(integerList);
    }

    private void reverseLastElementToFirst1(LinkedList<Integer> integerList) {
        Integer last = integerList.removeLast();
        integerList.addFirst(last);
    }

}
