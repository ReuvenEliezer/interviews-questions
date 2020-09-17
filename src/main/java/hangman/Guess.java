package hangman;

import java.util.HashSet;

public class Guess {
    private String wordSelected = "";
    private int charsNumber;
    private HashSet<Character> negativeChar = new HashSet<>();
    private HashSet<String> relevantWords = new HashSet<>();

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

    public HashSet<Character> getNegativeChar() {
        return negativeChar;
    }

    public void addNegativeChar(Character negativeChar) {
        if (!this.negativeChar.contains(negativeChar))
            this.negativeChar.add(negativeChar);
    }

    public void setNegativeChar(HashSet<Character> negativeChar) {
        this.negativeChar = negativeChar;
    }

    public HashSet<String> getRelevantWords() {
        return relevantWords;
    }

    public void setRelevantWords(HashSet<String> relevantWords) {
        this.relevantWords = relevantWords;
    }
}
