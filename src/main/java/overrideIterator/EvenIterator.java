package overrideIterator;

import java.util.Iterator;
import java.util.List;

public class EvenIterator implements Iterator<Integer> {
    private final List<Integer> list;
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

}
