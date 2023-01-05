import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListTest {

    @Test
    public void betterToUseLinkedListWhenMultiAddOrRemoveElementAndBetterToUseArrayListForSearchElementTest() {

        List<String> arrayList = new ArrayList<>();
        arrayList.add("a");
        arrayList.add("c");
        arrayList.add("d");
//        arrayList.add("b");
        arrayList.add(1, "b");

        System.out.println(arrayList);

        LinkedList<String> linkedList = new LinkedList<>();
        arrayList.add("a");
        arrayList.add("c");
        arrayList.add("d");
//        arrayList.add("b");
        arrayList.add(1, "b");
    }

    @Test
    public void listPartitionTest() {
        List<Integer> ints = IntStream.range(0, 10).boxed().collect(Collectors.toList());
        int batchSize = 3;
        List<List<Integer>> partition = Lists.partition(ints, batchSize);
        for (List<Integer> batch : partition) {
            System.out.println(batch);
        }
    }
}
