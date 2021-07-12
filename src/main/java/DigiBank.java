import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DigiBank {

//    enum v {
//        h(AllowedFollowersRuleHandler.class);
//
//        private Class<? extends DigiRuleHandler> aClass;
//        private DigiRuleHandler digiRuleHandler;
//
//        v(Class<? extends DigiRuleHandler> aClass) {
//            this.aClass = aClass;
//            this.digiRuleHandler = digiRuleHandler;
//        }
//
//        public Class<? extends DigiRuleHandler> getaClass() {
//            return aClass;
//        }
//
//        public DigiRuleHandler getDigiRuleHandler() {
//            return digiRuleHandler;
//        }
//    }

    private Map<Character, List<DigiRule>> characterToRulesMap = new HashMap<>();
    private static Map<Class<? extends DigiRule>, DigiRuleHandler> alarmConditionHandlerMap = new HashMap<>();
//            Arrays.stream(v.values()).map(v::getaClass,v::getDigiRuleHandler).{
//                    {AllowedFollowersRuleHandler.class, new AllowedFollowersRuleHandler()},
//            }).collect(Collectors.toMap(kv -> kv[0], kv -> kv[1]));


    /**
     * Char 	Allowed followers	Can the char be at the end of the word?
     * a	a,b,d	true
     * b	a,f	False
     * c	b	true
     */

    @Test
    public void test() {
        init();
        Assert.assertTrue(isValid("ad"));
        Assert.assertFalse(isValid("ah"));
        Assert.assertFalse(isValid("bab"));
        Assert.assertTrue(isValid("bf"));
        Assert.assertTrue(isValid("bfd"));
        Assert.assertFalse(isValid("bfb"));
    }

    private void init() {

        alarmConditionHandlerMap.put(AllowedFollowersRule.class, new AllowedFollowersRuleHandler());
        alarmConditionHandlerMap.put(EndWordRule.class, new EndWordRuleHandler());

        AllowedFollowersRule allowedFollowersRuleA = new AllowedFollowersRule('a', Stream.of('a', 'b', 'd').collect(Collectors.toSet()));
        EndWordRule endWordRuleA = new EndWordRule('a', true);

        EndWordRule endWordRuleB = new EndWordRule('b', false);
        EndWordRule endWordRuleC = new EndWordRule('c', true);

        AllowedFollowersRule allowedFollowersRuleB = new AllowedFollowersRule('a', Stream.of('a', 'f').collect(Collectors.toSet()));
        AllowedFollowersRule allowedFollowersRuleC = new AllowedFollowersRule('a', Collections.singleton('b'));

        characterToRulesMap.put('a', Stream.of(allowedFollowersRuleA, endWordRuleA).collect(Collectors.toList()));
        characterToRulesMap.put('b', Stream.of(allowedFollowersRuleB, endWordRuleB).collect(Collectors.toList()));
        characterToRulesMap.put('c', Stream.of(allowedFollowersRuleC, endWordRuleC).collect(Collectors.toList()));
    }


    private boolean isValid(String input) {
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            List<DigiRule> ruleList = characterToRulesMap.get(chars[i]);
            if (ruleList == null)
                continue;
            for (DigiRule digiRule : ruleList) {
                DigiRuleHandler digiRuleHandler = alarmConditionHandlerMap.get(digiRule.getRuleCondition());
                if (digiRuleHandler == null)
                    throw new IllegalStateException();
                if (!digiRuleHandler.checkRule(input, i, digiRule))
                    return false;
            }
        }
        return true;
    }


    class AllowedFollowersRule extends DigiRule {
        private Set<Character> AllowedFollowers;

        public AllowedFollowersRule(Character character, Set<Character> AllowedFollowers) {
            this.character = character;
            this.AllowedFollowers = AllowedFollowers;
        }

        @Override
        Class getRuleCondition() {
            return AllowedFollowersRule.class;
        }

    }


    class AllowedFollowersRuleHandler implements DigiRuleHandler {

        @Override
        public boolean checkRule(String input, int index, DigiRule digiRule) {
            AllowedFollowersRule followersRule = (AllowedFollowersRule) digiRule;
            if (index < input.length() - 1) {
                Set<Character> allowedFollowers = followersRule.AllowedFollowers;
                if (!allowedFollowers.contains(input.toCharArray()[index + 1])) {
                    return false;
                }
            }
            return true;
        }

//        @Override
//        public Class getRuleCondition() {
//            return AllowedFollowersRuleHandler.class;
//        }

    }

    class EndWordRuleHandler implements DigiRuleHandler {

        @Override
        public boolean checkRule(String input, int index, DigiRule digiRule) {
            EndWordRule endWordRule = (EndWordRule) digiRule;
            if (index == input.length() - 1) {
                if (!endWordRule.isAllowedAtEndWord) {
                    return false;
                }
            }
            return true;
        }

//        @Override
//        public Class getRuleCondition() {
//            return AllowedFollowersRuleHandler.class;
//        }

    }


    class EndWordRule extends DigiRule {
        private boolean isAllowedAtEndWord;

        public EndWordRule(Character character, boolean isAllowedAtEndWord) {
            this.character = character;
            this.isAllowedAtEndWord = isAllowedAtEndWord;
        }

        @Override
        Class getRuleCondition() {
            return EndWordRule.class;
        }
    }

    abstract class DigiRule {
        Character character;

        abstract Class getRuleCondition();
    }

    @FunctionalInterface
    interface DigiRuleHandler {
        boolean checkRule(String input, int index, DigiRule digiRule);
    }
}