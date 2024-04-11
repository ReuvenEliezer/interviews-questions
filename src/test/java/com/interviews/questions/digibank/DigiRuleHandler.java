package com.interviews.questions.digibank;

@FunctionalInterface
public interface DigiRuleHandler<T extends DigiRule> {
    boolean isValid(String input, int index, T digiRule);
}
