package spell;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

public class SpellCorrector implements ISpellCorrector{
    private Trie trie;
    Set<String> possibles;
    Set<String> valids;

    public SpellCorrector() {
        trie = new Trie();
        possibles = new HashSet<>();
        valids = new HashSet<>();
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File f = new File(dictionaryFileName);
        Scanner s = new Scanner(f);
        while(s.hasNext()){
            String st = s.next();
            trie.add(st);
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        String answer = null;
        possibles = new HashSet<>();
        valids = new HashSet<>();
        if(inputWord == null) return null;
        inputWord = inputWord.toLowerCase(Locale.ROOT);
        INode val = trie.find(inputWord);
        if(val != null) return inputWord;
        remove(inputWord);
        transpose(inputWord);
        wrong(inputWord);
        extra(inputWord);
        addValid(inputWord);
        if(valids.isEmpty()){
            Set<String> b = new HashSet<>(possibles);
            for(String a : b) {
                remove(a);
                transpose(a);
                wrong(a);
                extra(a);
            }
            addValid(inputWord);
        }
        answer = mostCommon();
        return answer;
    }
    private void remove(String inputWord){
        StringBuilder a;
        for(int i = 0; i < inputWord.length(); i++){
            a = new StringBuilder(inputWord);
            a.deleteCharAt(i);
            possibles.add(a.toString());
        }
    }
    private void transpose(String inputWord){
        if(inputWord.length() <= 1){
            return;
        } else {
            StringBuilder a;
            for(int i = 0; i < inputWord.length() - 1; i++){
                a = new StringBuilder(inputWord);
                char first = inputWord.charAt(i);
                char second = inputWord.charAt(i + 1);
                a.setCharAt(i, second);
                a.setCharAt(i + 1, first);
                possibles.add(a.toString());
            }
        }
    }
    private void wrong(String inputWord){
        StringBuilder a;
        for(int i = 0; i < inputWord.length(); i++){
            a = new StringBuilder(inputWord);
            for(int j = 0; j < 26; j++){
                a.setCharAt(i, (char) (j + 'a'));
                possibles.add(a.toString());
            }
        }
    }
    private void extra(String inputWord){
        StringBuilder a;
        for(int i = 0; i <= inputWord.length(); i++){
            for(int j = 0; j < 26; j++){
                a = new StringBuilder(inputWord);
                a.insert(i, (char) (j + 'a'));
                possibles.add(a.toString());
            }
        }
    }
    private void addValid(String inputWord){
        INode valid;
        for(String a : possibles){
           valid = trie.find(a);
           if(valid != null) valids.add(a);
        }
    }
    private String mostCommon(){
        StringBuilder b = new StringBuilder();
        int max = 0;
        if(valids.isEmpty()) return null;
        else{
            INode v;
            for(String a: possibles){
                v = trie.find(a);
                if(v == null){
                    continue;
                }
                else{
                    if(v.getValue() > max){
                        max = v.getValue();
                        b.setLength(0);
                        b.append(a);
                    }
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
}
