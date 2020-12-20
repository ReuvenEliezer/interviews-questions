import org.junit.Test;
import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.Map;

public class CommItTest {

    @Test
    public void fibTest() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("calcFibonnachi");
        System.out.println(calcFibonnachi(40));
        System.out.println(calcFibonnachi(40));
        System.out.println(calcFibonnachi(40));
        stopWatch.stop();
        stopWatch.start("calcFibonnachiOptimizeByMap");
        System.out.println(calcFibonnachiOptimizeByMap(40));
        System.out.println(calcFibonnachiOptimizeByMap(40));
        System.out.println(calcFibonnachiOptimizeByMap(40));
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    private Map<Long, Long> fibCalcResult = new HashMap<>();

    public long calcFibonnachi(long num) {
        if (num < 0)
            throw new UnsupportedOperationException(String.format("number %s not valid", num));

        if (num <= 1)
            return 1;

        return calcFibonnachi(num - 1) + calcFibonnachi(num - 2);
    }

    public long calcFibonnachiOptimizeByMap(long num) {
        if (num < 0)
            throw new UnsupportedOperationException(String.format("number %s not valid", num));

        if (num <= 1) {
            return fibCalcResult.put(num, 1l);
        }

        Long f1 = fibCalcResult.get(num - 1);
        if (f1 == null) {
            f1 = calcFibonnachi(num - 1);
            fibCalcResult.put(num - 1, f1);
        }


        Long f2 = fibCalcResult.get(num - 2);
        if (f2 == null) {
            f2 = calcFibonnachi(num - 2);
            fibCalcResult.put(num - 2, f2);
        }

        return f1 + f2;

    }
}
