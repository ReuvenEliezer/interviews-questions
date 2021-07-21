package digibank;

import java.util.Set;

public class AllowedFollowersRuleHandler implements DigiRuleHandler<AllowedFollowersRule> {

    @Override
    public boolean isValid(String input, int index, AllowedFollowersRule digiRule) {
        if (index < input.length() - 1) {
            Set<Character> allowedFollowers = digiRule.getAllowedFollowers();
            if (!allowedFollowers.contains(input.toCharArray()[index + 1])) {
                return false;
            }
        }
        return true;
    }
}

/**
 *   - registration service
 *   - login service
 *   - account service
 *   - portfolio service
 *   - operations service - buy/sell stocks actions
 *   - match/order book service -
 *   - notifications
 */