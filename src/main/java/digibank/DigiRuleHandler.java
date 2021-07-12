package digibank;

@FunctionalInterface
public interface DigiRuleHandler<T> {
    boolean checkRule(String input, int index, T digiRule);
}
