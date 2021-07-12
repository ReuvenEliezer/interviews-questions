package digibank;

class EndWordRuleHandler implements DigiRuleHandler {

    @Override
    public boolean checkRule(String input, int index, DigiRule digiRule) {
        if (index == input.length() - 1) {
            EndWordRule endWordRule = (EndWordRule) digiRule;
            if (!endWordRule.isAllowedAtEndWord()) {
                return false;
            }
        }
        return true;
    }

}
