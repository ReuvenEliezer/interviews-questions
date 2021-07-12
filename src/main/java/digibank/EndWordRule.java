package digibank;

public class EndWordRule extends DigiRule {
    private boolean isAllowedAtEndWord;

    public EndWordRule(Character character, boolean isAllowedAtEndWord) {
        this.character = character;
        this.isAllowedAtEndWord = isAllowedAtEndWord;
    }

    public boolean isAllowedAtEndWord() {
        return isAllowedAtEndWord;
    }

    @Override
    Class getRuleCondition() {
        return EndWordRule.class;
    }
}