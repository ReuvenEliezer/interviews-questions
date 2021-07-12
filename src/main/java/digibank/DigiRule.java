package digibank;

public abstract class DigiRule {
    protected Character character;

    protected abstract Class<? extends DigiRule> getRuleCondition();

}
