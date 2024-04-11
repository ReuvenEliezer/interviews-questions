package com.interviews.questions.callback;


public class Rule {

    private CreateRuleCallback callback;

    public CreateRuleCallback getCallback() {
        return callback;
    }

    public void setCallback(CreateRuleCallback callback) {
        this.callback = callback;
    }


    @FunctionalInterface
    public interface CreateRuleCallback {
        void invoke(Rule rule, Object metadata);
    }


}
