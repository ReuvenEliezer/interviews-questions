package com.interviews.questions.digibank;

import java.util.Map;
import java.util.Set;

public class DigiBankService {

    private Map<Character, Set<com.interviews.questions.digibank.DigiRule>> characterToRulesMap;

    public DigiBankService(Map<Character, Set<com.interviews.questions.digibank.DigiRule>> characterToRulesMap) {
        this.characterToRulesMap = characterToRulesMap;
    }


    public boolean isValid(String input) throws Exception {
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            Set<com.interviews.questions.digibank.DigiRule> ruleList = characterToRulesMap.get(chars[i]);
            if (ruleList == null)
                continue;
            for (DigiRule digiRule : ruleList) {
                Class<? extends com.interviews.questions.digibank.DigiRuleHandler> digiRuleHandler = RuleEnum.getDigiRuleHandler(digiRule.getRuleCondition());
                if (digiRuleHandler == null)
                    throw new UnsupportedOperationException(digiRule.getRuleCondition() + " not supported. missing handler for this");
                DigiRuleHandler handler = digiRuleHandler.getDeclaredConstructor().newInstance();
                if (!handler.isValid(input, i, digiRule))
                    return false;
            }
        }
        return true;
    }
}
