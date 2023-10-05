package hangman;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileUtils {

    private static HashSet<String> allWord = new HashSet<>();

    public static void setAllSortedWords() {
        String filePath = "src" + File.separator + "main" + File.separator + "java" + File.separator + "hangman" + File.separator;
        String fileName = "dictionary.txt";
        File file = new File(filePath + fileName);
        try (
                Stream<String> linesStream = Files.lines(file.toPath())) {
            List<String> collect = linesStream.map(String::toLowerCase).collect(Collectors.toList());
            Collections.sort(collect);
            allWord = (HashSet<String>) collect.stream().collect(Collectors.toSet());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashSet<String> getAllWord() {
        return allWord;
    }

    public static int getMaxWordLength(HashSet<String> allWords) {
        return Collections.max(allWords, Comparator.comparingInt(String::length)).length();
    }

    public static HashSet<String> getRelevantWordsByLength(int charsNumber) {
        HashSet<String> relevantDictionaryToSearch = getAllWord();
        HashSet<String> result = new HashSet<>();
        Iterator<String> iterator = relevantDictionaryToSearch.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (next.length() == charsNumber)
                result.add(next);
        }
        return result;
    }

    public static Character getMostCommonCharacter(Guess guess, Set<String> relevantDictionaryToSearch) {
        Map<Character, Integer> charsNumber = new HashMap<>();
        Set<Character> guessNegativeChar = guess.getNegativeChar();
        String wordSelected = guess.getWordSelected();

        Iterator<String> iterator = relevantDictionaryToSearch.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            for (char c : next.toCharArray()) {
                //if the char already checked in previous guess -> continue
                if (wordSelected.contains(Character.toString(c)))
                    continue;
                Optional<Character> first = guessNegativeChar.stream().filter(o -> o.equals(c)).findFirst();
                if (first.isPresent())
                    continue;
                if (charsNumber.containsKey(c)) {
                    charsNumber.put(c, charsNumber.get(c) + 1);
                } else {
                    charsNumber.put(c, 1);
                }
            }
        }
        return Collections.max(charsNumber.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }
}
