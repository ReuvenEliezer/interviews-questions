package digibank;

@FunctionalInterface
public interface DigiRuleHandler<T extends DigiRule> {
    boolean checkRule(String input, int index, T digiRule);
}
