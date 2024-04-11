package com.interviews.questions.digibank;

public class EndWordRule extends com.interviews.questions.digibank.DigiRule {
    private boolean isAllowedAtEndWord;

    public EndWordRule(Character character, boolean isAllowedAtEndWord) {
        this.character = character;
        this.isAllowedAtEndWord = isAllowedAtEndWord;
    }

    public boolean isAllowedAtEndWord() {
        return isAllowedAtEndWord;
    }

    @Override
    public Class<? extends DigiRule> getRuleCondition() {
        return EndWordRule.class;
    }

}