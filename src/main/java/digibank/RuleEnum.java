package digibank;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum RuleEnum {
    AllowedFollowersRule(AllowedFollowersRule.class, AllowedFollowersRuleHandler.class),
    EndWordRule(EndWordRule.class, EndWordRuleHandler.class);

    private Class<? extends DigiRule> digiRule;
    private Class<? extends DigiRuleHandler> digiRuleHandler;

    RuleEnum(Class<? extends DigiRule> digiRule, Class<? extends DigiRuleHandler> digiRuleHandler) {
        this.digiRule = digiRule;
        this.digiRuleHandler = digiRuleHandler;
    }

    public Class<? extends DigiRule> getDigiRule() {
        return digiRule;
    }

    public Class<? extends DigiRuleHandler> getDigiRuleHandler() {
        return digiRuleHandler;
    }

    private static Map<Class<? extends DigiRule>, Class<? extends DigiRuleHandler>> ruleToHandlerMap =
            Arrays.stream(RuleEnum.values()).collect(Collectors.toMap(RuleEnum::getDigiRule, RuleEnum::getDigiRuleHandler));

    public static Class<? extends DigiRuleHandler> getDigiRuleHandler(Class<? extends DigiRule> digiRule) {
        return ruleToHandlerMap.get(digiRule);
    }
}
