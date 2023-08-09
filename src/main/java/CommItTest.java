import org.junit.Test;
import org.springframework.util.StopWatch;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class CommItTest {

    @Test
    public void fibTest() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("calcFibonacci");
        System.out.println(calcFibonacci(40));
        System.out.println(calcFibonacci(40));
        System.out.println(calcFibonacci(40));
        stopWatch.stop();
        stopWatch.start("calcFibonacciOptimizeByMap");
        System.out.println(calcFibonacciOptimizeByMap(40));
        System.out.println(calcFibonacciOptimizeByMap(40));
        System.out.println(calcFibonacciOptimizeByMap(40));
        stopWatch.stop();
        System.out.println("getTotalTimeSeconds:" + stopWatch.getTotalTimeSeconds());
        System.out.println("Duration: " + Duration.ofMillis((long) (stopWatch.getTotalTimeSeconds() * 1000)));
        System.out.println(stopWatch.prettyPrint());
        System.out.println("fibCalcResult.size(): " + fibCalcResult.size());

    }

    private Map<Long, Long> fibCalcResult = new HashMap<>();

    public long calcFibonacci(long num) {
        if (num < 0)
            throw new UnsupportedOperationException(String.format("number %s not valid", num));

        if (num <= 1)
            return 1;

        return calcFibonacci(num - 1) + calcFibonacci(num - 2);
    }

    public long calcFibonacciOptimizeByMap(long num) {
        if (num < 0)
            throw new UnsupportedOperationException(String.format("number %s not valid", num));

        if (num <= 1) {
            return 1;
        }

        Long f1 = fibCalcResult.get(num - 1);
        if (f1 == null) {
            f1 = calcFibonacci(num - 1);
            fibCalcResult.put(num - 1, f1);
        }


        Long f2 = fibCalcResult.get(num - 2);
        if (f2 == null) {
            f2 = calcFibonacci(num - 2);
            fibCalcResult.put(num - 2, f2);
        }

        return f1 + f2;

    }
}
