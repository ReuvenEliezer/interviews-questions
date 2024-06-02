package com.interviews.questions.digibank;

public class EndWordRule extends DigiRule {
    private final boolean isAllowedAtEndWord;

    public EndWordRule(Character character, boolean isAllowedAtEndWord) {
        super(character);
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