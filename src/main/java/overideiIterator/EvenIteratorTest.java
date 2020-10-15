package overideiIterator;

import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EvenIteratorTest {
    //radware
    @Test
    public void evenIteratorPrinterTest(){
        EvenIterator evenIterator= new EvenIterator(IntStream.range(1,10).boxed().collect(Collectors.toList()));
        while (evenIterator.hasNext()){
            System.out.println(evenIterator.next());
        }
    }
}
