package digibank;

import java.util.Objects;
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
    public Class<? extends DigiRule> getRuleCondition() {
        return AllowedFollowersRule.class;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllowedFollowersRule that = (AllowedFollowersRule) o;
        return Objects.equals(character, that.character);
    }

    @Override
    public int hashCode() {
        return Objects.hash(character);
    }
}