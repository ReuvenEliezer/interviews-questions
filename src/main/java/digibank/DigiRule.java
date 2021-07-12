package digibank;

import java.util.Objects;

public abstract class DigiRule {
    protected Character character;

    protected abstract Class<? extends DigiRule> getRuleCondition();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DigiRule digiRule = (DigiRule) o;
        return Objects.equals(character, digiRule.character);
    }

    @Override
    public int hashCode() {
        return Objects.hash(character);
    }
}
