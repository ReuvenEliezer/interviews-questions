package com.interviews.questions.hangman;

import org.junit.Test;

import java.util.*;

public class HangmanPlayer {

    @Test
    public void start() {
        //init all words
        com.interviews.questions.hangman.FileUtils.setAllSortedWords();

        String selectedWord = com.interviews.questions.hangman.HangmanService.startGetSelectedWord();
//        int maxWordLength = FileUtils.getMaxWordLength(allWords);
        com.interviews.questions.hangman.Guess guess = new com.interviews.questions.hangman.Guess(selectedWord.length());
        Set<String> relevantWords = com.interviews.questions.hangman.FileUtils.getRelevantWordsByLength(guess.getCharsNumber());
        guess.setRelevantWords(relevantWords);
        guessCharRecursive(guess);
    }

    private void guessCharRecursive(Guess guess) {
        if (!guess.getWordSelected().contains("_")) {
            System.out.println("done !! the final result of word selected is: " + guess.getWordSelected());
            return;
        }
        Set<String> relevantWords = guess.getRelevantWords();
        Character mostCommonCharacter = FileUtils.getMostCommonCharacter(guess, relevantWords);
        GuessResult guessResult = HangmanService.checkWord(mostCommonCharacter);
        if (guessResult.isSuccess()) {
            String result = guessResult.getResult();
            guess.setWordSelected(result);
        } else {
            guess.addNegativeChar(mostCommonCharacter);
        }
        guessCharRecursive(guess);
    }


//    @Test
//    public void Replace() {
//        String s = "abb";
//        String a = "___";
//        char b = 'b';
//        StringBuilder myName = new StringBuilder(a);
//        for (int i = 0; i < s.length(); i++) {
//            if (b == s.charAt(i)) {
//                myName.setCharAt(i, b);
////                String my_new_str = a.replace('_',b );
////                String substring = s.substring(i, i+1);
////                String result = a.replace(a, "b;");
////                a.charAt(i);
//            }
//        }
//        System.out.println(myName);
//    }

//    @Test
//    public void Test() {
//        String s = "abc";
//        String a = "___";
//        Character b = 'b';
//        for (int i = 0; i < s.length(); i++) {
//            if (b.equals(s.charAt(i)))
//                a.charAt(i);
//        }
//        System.out.println(s);
//    }
}
