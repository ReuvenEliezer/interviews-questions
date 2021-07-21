package digibank;

class EndWordRuleHandler implements DigiRuleHandler<EndWordRule> {

    @Override
    public boolean isValid(String input, int index, EndWordRule digiRule) {
        if (index == input.length() - 1) {
            if (!digiRule.isAllowedAtEndWord()) {
                return false;
            }
        }
        return true;
    }

}
