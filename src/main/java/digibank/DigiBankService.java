package digibank;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DigiBankService {
    private Map<Character, Set<DigiRule>> characterToRulesMap = new HashMap<>();

    public void init() {

        EndWordRule endWordRuleA = new EndWordRule('a', true);
        EndWordRule endWordRuleB = new EndWordRule('b', false);
        EndWordRule endWordRuleC = new EndWordRule('c', true);

        AllowedFollowersRule allowedFollowersRuleA = new AllowedFollowersRule('a', Stream.of('a', 'b', 'd').collect(Collectors.toSet()));
        AllowedFollowersRule allowedFollowersRuleB = new AllowedFollowersRule('b', Stream.of('a', 'f').collect(Collectors.toSet()));
        AllowedFollowersRule allowedFollowersRuleC = new AllowedFollowersRule('c', Collections.singleton('b'));

        characterToRulesMap.put('a', Stream.of(allowedFollowersRuleA, endWordRuleA).collect(Collectors.toSet()));
        characterToRulesMap.put('b', Stream.of(allowedFollowersRuleB, endWordRuleB).collect(Collectors.toSet()));
        characterToRulesMap.put('c', Stream.of(allowedFollowersRuleC, endWordRuleC).collect(Collectors.toSet()));
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
