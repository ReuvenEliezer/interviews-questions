package com.interviews.questions.digibank;


import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum RuleEnum {
    AllowedFollowersRule(com.interviews.questions.digibank.AllowedFollowersRule.class, AllowedFollowersRuleHandler.class),
    EndWordRule(com.interviews.questions.digibank.EndWordRule.class, com.interviews.questions.digibank.EndWordRuleHandler.class);

    private Class<? extends com.interviews.questions.digibank.DigiRule> digiRule;
    private Class<? extends com.interviews.questions.digibank.DigiRuleHandler> digiRuleHandler;

    RuleEnum(Class<? extends com.interviews.questions.digibank.DigiRule> digiRule, Class<? extends com.interviews.questions.digibank.DigiRuleHandler> digiRuleHandler) {
        this.digiRule = digiRule;
        this.digiRuleHandler = digiRuleHandler;
    }

    public Class<? extends com.interviews.questions.digibank.DigiRule> getDigiRule() {
        return digiRule;
    }

    public Class<? extends com.interviews.questions.digibank.DigiRuleHandler> getDigiRuleHandler() {
        return digiRuleHandler;
    }

    private static Map<Class<? extends com.interviews.questions.digibank.DigiRule>, Class<? extends com.interviews.questions.digibank.DigiRuleHandler>> ruleToHandlerMap =
            Arrays.stream(RuleEnum.values()).collect(Collectors.toMap(RuleEnum::getDigiRule, RuleEnum::getDigiRuleHandler));

    public static Class<? extends DigiRuleHandler> getDigiRuleHandler(Class<? extends DigiRule> digiRule) {
        return ruleToHandlerMap.get(digiRule);
    }
}
