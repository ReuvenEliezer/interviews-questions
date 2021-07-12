package digibank;

@FunctionalInterface
public
interface DigiRuleHandler {
    boolean checkRule(String input, int index, DigiRule digiRule);
}
