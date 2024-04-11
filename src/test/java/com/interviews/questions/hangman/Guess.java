package com.interviews.questions.hangman;

import java.util.HashSet;
import java.util.Set;

public class Guess {
    private String wordSelected = "";
    private int charsNumber;
    private Set<Character> negativeChar = new HashSet<>();
    private Set<String> relevantWords = new HashSet<>();

    public Guess(int charsNumber) {
        for (int i = 0; i < charsNumber; i++) {
            this.wordSelected += "_";
        }
        this.charsNumber = charsNumber;
    }

    public String getWordSelected() {
        return wordSelected;
    }

    public void setWordSelected(String wordSelected) {
        this.wordSelected = wordSelected;
    }

    public int getCharsNumber() {
        return charsNumber;
    }

    public void setCharsNumber(int charsNumber) {
        this.charsNumber = charsNumber;
    }

    public Set<Character> getNegativeChar() {
        return negativeChar;
    }

    public void addNegativeChar(Character negativeChar) {
        this.negativeChar.add(negativeChar);
    }

    public void setNegativeChar(Set<Character> negativeChar) {
        this.negativeChar = negativeChar;
    }

    public Set<String> getRelevantWords() {
        return relevantWords;
    }

    public void setRelevantWords(Set<String> relevantWords) {
        this.relevantWords = relevantWords;
    }
}
