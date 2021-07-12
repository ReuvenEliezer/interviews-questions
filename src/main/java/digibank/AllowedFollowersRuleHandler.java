package digibank;

import java.util.Set;

public class AllowedFollowersRuleHandler implements DigiRuleHandler {

    @Override
    public boolean checkRule(String input, int index, DigiRule digiRule) {
        if (index < input.length() - 1) {
            AllowedFollowersRule followersRule = (AllowedFollowersRule) digiRule;
            Set<Character> allowedFollowers = followersRule.getAllowedFollowers();
            if (!allowedFollowers.contains(input.toCharArray()[index + 1])) {
                return false;
            }
        }
        return true;
    }
}
