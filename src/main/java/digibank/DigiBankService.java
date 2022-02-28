package digibank;

import java.util.Map;
import java.util.Set;

public class DigiBankService {

    private Map<Character, Set<DigiRule>> characterToRulesMap;

    public DigiBankService(Map<Character, Set<DigiRule>> characterToRulesMap) {
        this.characterToRulesMap = characterToRulesMap;
    }


    public boolean isValid(String input) throws InstantiationException, IllegalAccessException {
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            Set<DigiRule> ruleList = characterToRulesMap.get(chars[i]);
            if (ruleList == null)
                continue;
            for (DigiRule digiRule : ruleList) {
                Class<? extends DigiRuleHandler> digiRuleHandler = RuleEnum.getDigiRuleHandler(digiRule.getRuleCondition());
                if (digiRuleHandler == null)
                    throw new UnsupportedOperationException(digiRule.getRuleCondition()+ " not supported. missing handler for this");
                DigiRuleHandler handler = digiRuleHandler.newInstance();
                if (!handler.isValid(input, i, digiRule))
                    return false;
            }
        }
        return true;
    }
}
