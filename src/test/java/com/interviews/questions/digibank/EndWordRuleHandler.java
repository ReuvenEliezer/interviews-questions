package com.interviews.questions.digibank;

class EndWordRuleHandler implements DigiRuleHandler<com.interviews.questions.digibank.EndWordRule> {

    @Override
    public boolean isValid(String input, int index, EndWordRule digiRule) {
        if (index == input.length() - 1) {
            if (!digiRule.isAllowedAtEndWord()) {
                return false;
            }
        }
        return true;
    }

}
