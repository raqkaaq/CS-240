package spell;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SpellCorrector implements ISpellCorrector {
    private final Trie trie;
    Set<String> possibles;
    Set<String> valid;

    public SpellCorrector() {
        this.trie = new Trie();
        this.possibles = new HashSet<>();
        this.valid = new HashSet<>();
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File f = new File(dictionaryFileName);
        Scanner scan = new Scanner(f);
        while (scan.hasNext()) {
            String s = scan.next();
            trie.add(s);
        }
        //System.out.print(trie.toString()); //prints the words in the trie
    }

    @Override
    public String suggestSimilarWord(String inputWord) throws IOException {
        String correct = null;
        possibles = new HashSet<>();
        valid = new HashSet<>();
        if (inputWord == null) return null;
        inputWord = inputWord.toLowerCase(Locale.ROOT);
        INode val = trie.find(inputWord);
        if(val != null){
            return inputWord;
        }
        removeLetter(inputWord);
        wrongLetter(inputWord);
        transposeLetter(inputWord);
        extraLetter(inputWord);
        addValid(inputWord);
        if(valid.isEmpty()) {
            Set<String> b = new HashSet<>(possibles);
            for(String a : b) {
                removeLetter(a);
                wrongLetter(a);
                transposeLetter(a);
                extraLetter(a);
            }
            addValid(inputWord);
        }
        correct = maxString();
        return correct;
    }
    private void addValid(String inputWord){
        INode val;
        for(String a : possibles){
            val = trie.find(a);
            if(val != null){
                valid.add(a);
            }
        }
    }
    private String maxString(){
        StringBuilder b = new StringBuilder();
        int max = 0;
        if(valid.isEmpty()){
            return null;
        }
        if(!valid.isEmpty()){
            INode v;
            for(String a : valid){
                v = trie.find(a);
                if(v == null)
                    continue;
                else{
                    //update max
                    if(v.getValue() > max){
                        max = v.getValue();
                        b.setLength(0);
                        b.append(a);
                    }
                    //get the strings by alphabetic priority
                    if(v.getValue() == max){
                        if(b.toString().compareTo(a) > 0){
                            max = v.getValue();
                            b.setLength(0);
                            b.append(a);
                        }
                    }
                }
            }
        }
        return b.toString();
    }

    private void removeLetter(String word) {
        StringBuilder a;
        for(int i = 0; i < word.length(); i++){
            a = new StringBuilder(word);
            a = a.deleteCharAt(i);
            possibles.add(a.toString());
        }
    }
    private void wrongLetter(String word){
        StringBuilder a;
        for(int i = 0; i < word.length(); i++){
            a = new StringBuilder(word);
            for(int j = 0; j < 26; j++){
                a.setCharAt(i, (char)(j + 'a'));
                possibles.add(a.toString());
            }
        }
    }
    private void transposeLetter(String word){
        if(word.length() == 1){
            return;
        } else {
            StringBuilder a;
            for(int i = 0; i < word.length() - 1; i++){
                a = new StringBuilder(word);
                char firstChar = a.charAt(i);
                char secondChar = a.charAt(i + 1);
                a.setCharAt(i, secondChar);
                a.setCharAt(i + 1, firstChar);
                possibles.add(a.toString());
            }
        }
    }
    public void extraLetter(String word) {
        StringBuilder a;
        for(int i = 0; i <= word.length(); i++){
            for(int j = 0; j < 26; j++){
                a = new StringBuilder(word);
                a.insert(i, (char)(j + 'a'));
                possibles.add(a.toString());
            }
        }
    }
}

//remove letter
    //transpose letter
    //extra letter
    //wrong letter
