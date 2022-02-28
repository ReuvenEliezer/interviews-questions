package digibank;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DigiBankTest {


    /**
     * write the program that get a string and returns boolean if the String is valid.
     * the validation will be decided by all condition rules as below: if one condition is false = the func will be return false.
     * The design should be supported so that additional conditions can be added easily
     *
     * the table below contains a rules for a char, for each char can be more that one rule.
     * for each char in the input "String" - the func will be validate all chars in input string as table described
     *
     * example 1:
     * input "add" - for 'a' char there is 2 rules:
     *      first rule: The next character after it can only be from 'Allowed followers' list, if the next char is no in list return "not valid",
     *          (in our example - the next char after 'a' is 'd' and it exists in the 'allowed follower' list
     *      second rule: the end of input must be end with this character. if not return false (in our example - there is no rule for 'd' char)
     *     - so, the func should returns true
     *
     * example 2:
     *      input 'ab'
     *      first rule 'Allowed followers' will be return true
     *      second rule 'Can the char be at the end of the word?' return false
     *      - so, the func should returns false
     *
     *
     * Char |   Allowed followers  |  Can the char be at the end of the word?
     * a	|   a,b,d             |  true
     * b	|   a,f               |  False
     * c	|   b	              |  true
     */


    @Test
    public void test() throws Exception {

        EndWordRule endWordRuleA = new EndWordRule('a', true);
        EndWordRule endWordRuleB = new EndWordRule('b', false);
        EndWordRule endWordRuleC = new EndWordRule('c', true);

        AllowedFollowersRule allowedFollowersRuleA = new AllowedFollowersRule('a', Stream.of('a', 'b', 'd').collect(Collectors.toSet()));
        AllowedFollowersRule allowedFollowersRuleB = new AllowedFollowersRule('b', Stream.of('a', 'f').collect(Collectors.toSet()));
        AllowedFollowersRule allowedFollowersRuleC = new AllowedFollowersRule('c', Collections.singleton('b'));

         Map<Character, Set<DigiRule>> characterToRulesMap = new HashMap<>();

        characterToRulesMap.put('a', Stream.of(allowedFollowersRuleA, endWordRuleA).collect(Collectors.toSet()));
        characterToRulesMap.put('b', Stream.of(allowedFollowersRuleB, endWordRuleB).collect(Collectors.toSet()));
        characterToRulesMap.put('c', Stream.of(allowedFollowersRuleC, endWordRuleC).collect(Collectors.toSet()));

        DigiBankService digiBankService = new DigiBankService(characterToRulesMap);

        Assert.assertTrue(digiBankService.isValid("add"));
        Assert.assertTrue(digiBankService.isValid("ad"));
        Assert.assertFalse(digiBankService.isValid("ab"));
        Assert.assertFalse(digiBankService.isValid("ah"));
        Assert.assertFalse(digiBankService.isValid("bab"));
        Assert.assertTrue(digiBankService.isValid("bf"));
        Assert.assertTrue(digiBankService.isValid("bfd"));
        Assert.assertFalse(digiBankService.isValid("bfb"));
    }


}
