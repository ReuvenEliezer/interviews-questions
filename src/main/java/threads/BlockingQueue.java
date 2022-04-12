package threads;

import java.util.ArrayList;
import java.util.List;

public class BlockingQueue<T> {
    private List<T> list = new ArrayList<>();

    public synchronized void put(T t) {
        list.add(t);
        notifyAll();
    }

    public synchronized T get() throws InterruptedException {
        while (list.isEmpty()) wait();
        return list.get(0);
    }

}
