package overideiIterator;

import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EvenIterator implements Iterator<Integer> {
    private List<Integer> list;
    private int current = 0;

    public EvenIterator(List<Integer> list) {
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        while (list.size() > current) {
            if (list.get(current) % 2 == 0) {
                return true;
            }
            current++;
        }
        return false;
    }

    @Override
    public Integer next() {
        return list.get(current++);
    }

    @Test
    public void evenIteratorPrinterTest() {
        List<Integer> integerList = IntStream.range(0, 10).boxed().collect(Collectors.toList());
        EvenIterator evenIterator = new EvenIterator(integerList);
        while (evenIterator.hasNext()) {
            System.out.println(evenIterator.next());
        }
    }
}
