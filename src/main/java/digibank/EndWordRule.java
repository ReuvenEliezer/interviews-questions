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
    public Class<? extends DigiRule> getRuleCondition() {
        return EndWordRule.class;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        EndWordRule that = (EndWordRule) o;
//        return Objects.equals(character, that.character);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(character);
//    }
}