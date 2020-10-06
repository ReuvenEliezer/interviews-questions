package callback;

import org.junit.Test;

public class CallBackTest {

    @Test
    public void test(){
//        ArrayList <Integer> integers = new ArrayList<>();
//        integers.add(1);
//        integers.remove(new Integer(1));
        Rule rule = new Rule();

        rule.setCallback((alarmRule, metadata) -> {
            System.out.println("do something");
        });

        rule.getCallback().invoke(rule, null);

    }
}
