import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class Earnix {

    @Test
    public void test() {
        MyCollection<String> myCollection = new MyCollection();

        myCollection.set(0, "a");
        myCollection.setAll("b");
        myCollection.set(1, "c");

        for (int i = 0; i < 5; i++) {
            System.out.println(myCollection.get(i));
        }

    }


    class MyCollection<T> {

        private Map<Integer, T> indexToValueMap;
        private T value = null;

        public MyCollection() {
            this.indexToValueMap = new HashMap<>();
        }

        public T set(Integer index, T value) {
            return indexToValueMap.put(index, value);
        }

        public void setAll(T value) {
            this.value = value;
            indexToValueMap = new HashMap<>();
        }

        public T get(Integer index) {
            return indexToValueMap.getOrDefault(index, value);
        }
    }
}
