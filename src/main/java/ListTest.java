import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListTest {

    @Test
    public void betterToUseLinkedListWhenMultiAddOrRemoveElementAndBetterToUseArrayListForSearchElementTest(){
        List<String> arrayList= new ArrayList<>();
        arrayList.add("a");
        arrayList.add("c");
        arrayList.add("d");
//        arrayList.add("b");
        arrayList.add(1,"b");

        System.out.println(arrayList);

        LinkedList<String> linkedList = new LinkedList<>();
        arrayList.add("a");
        arrayList.add("c");
        arrayList.add("d");
//        arrayList.add("b");
        arrayList.add(1,"b");
    }
}
