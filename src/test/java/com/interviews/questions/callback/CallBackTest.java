package com.interviews.questions.callback;

import org.junit.Test;

public class CallBackTest {

    @Test
    public void test(){
        com.interviews.questions.callback.Rule rule = new Rule();

        rule.setCallback((rule1, metadata) -> {
            System.out.println("do something");
        });

        rule.getCallback().invoke(rule, null);
    }

}
