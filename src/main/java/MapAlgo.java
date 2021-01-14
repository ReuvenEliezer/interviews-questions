import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

public class MapAlgo {

    /**
     * https://practice.geeksforgeeks.org/problems/longest-k-unique-characters-substring/0/?category[]=Map&category[]=Map&problemStatus=unsolved&page=1&query=category[]MapproblemStatusunsolvedpage1category[]Map
     *
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
//    public void longest_K_uniqueCharactersSubstringTest() throws IOException {
//        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
//        String s1 = read.readLine();
//        int tc = Integer.parseInt(s1);
//        while (tc-- > 0) {
//            String s = read.readLine();
//            int k = Integer.valueOf(read.readLine());
        Assert.assertEquals(1, findLongest_K_uniqueCharactersSubstring("nwnk", 1));

        Assert.assertEquals(4, findLongest_K_uniqueCharactersSubstring("aaaa", 1));

        Assert.assertEquals(7, findLongest_K_uniqueCharactersSubstring("nnknkkk", 2));
        Assert.assertEquals(7, findLongest_K_uniqueCharactersSubstring("aabacbebebe", 3));
//        System.out.println(result);
//        }
    }

    private static int findLongest_K_uniqueCharactersSubstring(String str, int k) {
        if (k > str.length()) return -1;
        Map<Integer, CharFollow> map = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            map.put(i, new CharFollow(str.charAt(i)));
        }

        for (Map.Entry<Integer, CharFollow> entry : map.entrySet()) {
            Integer index = entry.getKey();
            CharFollow charFollow = entry.getValue();
            Set<Character> follows = charFollow.uniqueFollows;
            for (int j = index + 1; j < map.size(); j++, charFollow.totalFollow++) {
                CharFollow nextChar = map.get(j);
                if (follows.size() == k && !follows.contains(nextChar.lead)) {
                    break;
                }
                follows.add(nextChar.lead);
            }
        }

        int totalFollow = map.entrySet().stream().max((entry1, entry2) -> entry1.getValue().totalFollow > entry2.getValue().totalFollow ? 1 : -1).get().getValue().totalFollow;
        return totalFollow == 0 ? -1 : totalFollow;
    }

    @Test
    public void f() {
//https://practice.geeksforgeeks.org/problems/nuts-and-bolts-problem0431/1/?track=hashing-interview&batchId=117
        char nuts[] = {'@', '%', '$', '#', '^'};
        char bolts[] = {'%', '@', '#', '$', '^'};
        matchPairs(nuts, bolts);

    }


    public void matchPairs(char nuts[], char bolts[]) {
        Map<Character, Integer> map = new LinkedHashMap<>();
        for (int i = 0; i < nuts.length; i++) {
            map.put(nuts[i],i);
        }

        for (int i = 0; i < bolts.length; i++) {
            if (!map.containsKey(bolts[i])){
                map.remove(bolts[i]);
            }
        }

        map.entrySet().forEach( e->System.out.print(e.getKey()+ " "));
    }

}

class CharFollow {
    char lead;
    Set<Character> uniqueFollows = new HashSet<>();
    int totalFollow = 1;

    public CharFollow(char leadChar) {
        this.lead = leadChar;
        this.uniqueFollows.add(leadChar);
    }

}
