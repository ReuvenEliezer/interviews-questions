package com.interviews.questions.hangman;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class HangmanService {

    private static String wordSelected;
    private static StringBuilder wordGuess = new StringBuilder();


    public static String startGetSelectedWord() {
        Set<String> allWords = FileUtils.getAllWord();
        List<String> allWordsList = allWords.stream().toList();
        int randomNum = ThreadLocalRandom.current().nextInt(0, allWords.size());
        wordSelected = allWordsList.get(randomNum);
//        wordSelected ="gaga";
        wordGuess.append("_".repeat(wordSelected.length()));

        System.out.println("wordSelected result by hangman service: " + wordSelected);
        return wordSelected;
    }

    public static com.interviews.questions.hangman.GuessResult checkWord(char c) {
        System.out.println("check char: " + c);
        com.interviews.questions.hangman.GuessResult guessResult = new GuessResult();
        for (int i = 0; i < wordSelected.length(); i++) {
            if (wordSelected.charAt(i) == c) {
                guessResult.setSuccess(true);
                wordGuess.setCharAt(i, c);
            }
        }
        guessResult.setResult(wordGuess.toString());
        System.out.println("temp result: " + guessResult.getResult());
        return guessResult;
    }
}
