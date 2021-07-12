package digibank;

import java.util.Set;

public class AllowedFollowersRule extends DigiRule {
    private Set<Character> allowedFollowers;

    public AllowedFollowersRule(Character character, Set<Character> allowedFollowers) {
        this.character = character;
        this.allowedFollowers = allowedFollowers;
    }

    public Set<Character> getAllowedFollowers() {
        return allowedFollowers;
    }

    @Override
    Class getRuleCondition() {
        return AllowedFollowersRule.class;
    }
}