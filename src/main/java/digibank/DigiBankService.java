package digibank;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DigiBankService {
    private Map<Character, List<DigiRule>> characterToRulesMap = new HashMap<>();

    public void init() {

        EndWordRule endWordRuleA = new EndWordRule('a', true);
        EndWordRule endWordRuleB = new EndWordRule('b', false);
        EndWordRule endWordRuleC = new EndWordRule('c', true);

        AllowedFollowersRule allowedFollowersRuleA = new AllowedFollowersRule('a', Stream.of('a', 'b', 'd').collect(Collectors.toSet()));
        AllowedFollowersRule allowedFollowersRuleB = new AllowedFollowersRule('a', Stream.of('a', 'f').collect(Collectors.toSet()));
        AllowedFollowersRule allowedFollowersRuleC = new AllowedFollowersRule('a', Collections.singleton('b'));

        characterToRulesMap.put('a', Stream.of(allowedFollowersRuleA, endWordRuleA).collect(Collectors.toList()));
        characterToRulesMap.put('b', Stream.of(allowedFollowersRuleB, endWordRuleB).collect(Collectors.toList()));
        characterToRulesMap.put('c', Stream.of(allowedFollowersRuleC, endWordRuleC).collect(Collectors.toList()));
    }


    public boolean isValid(String input) throws InstantiationException, IllegalAccessException {
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            List<DigiRule> ruleList = characterToRulesMap.get(chars[i]);
            if (ruleList == null)
                continue;
            for (DigiRule digiRule : ruleList) {
                Class<? extends DigiRuleHandler> digiRuleHandler = RuleEnum.getDigiRuleHandler(digiRule.getRuleCondition());

                if (digiRuleHandler == null)
                    throw new IllegalStateException();
                DigiRuleHandler handler = digiRuleHandler.newInstance();
                if (!handler.checkRule(input, i, digiRule))
                    return false;
            }
        }
        return true;
    }
}
