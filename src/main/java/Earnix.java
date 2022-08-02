import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class Earnix {

    @Test
    public void test() {
        //write a collection utils that support the following condition:
        //set will be set the given value into the given index
        //setAll will be set the given value into all indexes
        //get func will be retrieve the value in o(1) complexity
        MyCollection<String> myCollection = new MyCollection<>();

        assertThat(myCollection.get(0)).isNull();

        myCollection.set(0, "a");

        assertThat(myCollection.get(0)).isEqualTo("a");
        assertThat(myCollection.get(1)).isNull();

        myCollection.setAll("b");
        assertThat(myCollection.get(0)).isEqualTo("b");
        assertThat(myCollection.get(1)).isEqualTo("b");
        assertThat(myCollection.get(2)).isEqualTo("b");

        myCollection.set(1, "c");
        assertThat(myCollection.get(0)).isEqualTo("b");
        assertThat(myCollection.get(1)).isEqualTo("c");
        assertThat(myCollection.get(2)).isEqualTo("b");

        for (int i = 0; i < 5; i++) {
            System.out.println(myCollection.get(i));
        }

    }


    static class MyCollection<T> {

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
