package com.interviews.questions.digibank;

import java.util.Set;

public class AllowedFollowersRule extends DigiRule {
    private final Set<Character> allowedFollowers;

    public AllowedFollowersRule(Character character, Set<Character> allowedFollowers) {
        super(character);
        this.allowedFollowers = allowedFollowers;
    }

    public Set<Character> getAllowedFollowers() {
        return allowedFollowers;
    }

    @Override
    public Class<? extends DigiRule> getRuleCondition() {
        return AllowedFollowersRule.class;
    }

}