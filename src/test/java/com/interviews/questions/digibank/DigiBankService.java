package com.interviews.questions.digibank;

import java.util.Map;
import java.util.Set;

public class DigiBankService {

    private final Map<Character, Set<DigiRule>> characterToRulesMap;

    public DigiBankService(Map<Character, Set<DigiRule>> characterToRulesMap) {
        this.characterToRulesMap = characterToRulesMap;
    }


    public boolean isValid(String input) throws Exception {
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            Set<DigiRule> ruleList = characterToRulesMap.get(chars[i]);
            if (ruleList == null)
                continue;
            for (DigiRule digiRule : ruleList) {
                Class<? extends DigiRuleHandler> digiRuleHandler = RuleEnum.getDigiRuleHandler(digiRule.getRuleCondition());
                if (digiRuleHandler == null)
                    throw new UnsupportedOperationException("Missing handle for rule:" + digiRule.getRuleCondition().getSimpleName());
                DigiRuleHandler handler = digiRuleHandler.getDeclaredConstructor().newInstance();
                if (!handler.isValid(input, i, digiRule))
                    return false;
            }
        }
        return true;
    }
}
