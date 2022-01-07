package spell;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;

public class SpellCorrector implements ISpellCorrector{
    private final Trie trie;
    private Words possibles;
    private Words valid;
    private Words lastPossibility;
    public SpellCorrector() {
        this.trie = new Trie();
        this.possibles = new Words();
        this.valid = new Words();
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File f = new File(dictionaryFileName);
        Scanner scan = new Scanner(f);
        while(scan.hasNext()){
            String s = scan.next();
            trie.add(s);
        }
        //System.out.print(trie.toString()); //prints the words in the trie
    }
    public void fileOutput() throws IOException{
        FileOutputStream os = new FileOutputStream("out.txt");
        byte[] s = possibles.toString().getBytes(StandardCharsets.UTF_8);
        os.write(s);
        os.write("\n\n\n\n\n".getBytes(StandardCharsets.UTF_8));
        byte[] k = valid.toString().getBytes(StandardCharsets.UTF_8);
        os.write(k);
        os.close();
    }
    @Override
    public String suggestSimilarWord(String inputWord) throws IOException {
        String correct = null;
        //WrapInt count = new WrapInt();
       // count.value = 0;
        if(inputWord == null) return null;
        inputWord = inputWord.toLowerCase(Locale.ROOT);
        //if(trie.find(inputWord) != null) return inputWord;
        INode val = trie.find(inputWord);
        int v = 0;
        if(val != null) v = val.getValue();
        possibles.startWord(inputWord, v);
        Words last = possibles;
        last = wrongLetter(inputWord,last);
        //last = extraLetter(inputWord,last);
        //last = removeChar(inputWord, last);
        //last = transposeLetters(inputWord, last);
        correct = addValid();
        //fileOutput();
       // System.out.println(count.value + 1);
        lastPossibility = last;
        if(correct == null){
            Words a = possibles;
            String b;
            while(!a.equals(lastPossibility)){
                b = a.getWord();
                last = wrongLetter(b, last);
               // last = extraLetter(b, last);
                //last = removeChar(b, last);
                //last = transposeLetters(b, last);
                if(a.getNext() != null) a = a.getNext();
                //System.out.println(count.value);
            }
            correct = addValid();
            fileOutput();
            if (correct == null) return null;
        }
        if(valid.getNext() == null) return valid.getWord();
        return correct;
    }
    private String addValid() {
        int max = 0;
        String mcw = null;
        Words maybeValid = valid;
        while(maybeValid.getNext() != null){
            maybeValid = maybeValid.getNext();
        }
        Words a = possibles.getNext();
        while(a != lastPossibility){
            if(a.getTimes() > 0){
                Words extra = new Words(a.getWord(), a.getTimes());
                if(maybeValid == null) maybeValid = extra;
                if(valid.getWord() == null) valid = maybeValid;
                else maybeValid.setNext(extra, possibles);
                maybeValid = maybeValid.getNext();
            }
            if(a.getTimes() > max){
                max = a.getTimes();
                mcw = a.getWord();
                System.out.println(max + mcw);
            }
            a = a.getNext();
        }
        return mcw;
    }
    private Words extraLetter(String word, Words last){
        StringBuilder nw = new StringBuilder();
        char l;
        int index = 0;
        Words currWord = last;
        for(int i = 0; i < word.length() + 1; ++i){
            for(int j = 0; j < 26; j++){
                l = (char) ('a' + j);
                nw.append(word);
                nw.insert(i, l);
                INode t = trie.find(nw.toString());
                if(t != null) index = t.getValue();
                Words aWord = new Words(nw.toString(), index);
                currWord.setNext(aWord, possibles);
                if(currWord.getNext() != null) currWord = currWord.getNext();
                nw = new StringBuilder();
                index = 0;
            }
        }
        last = currWord;
        return last;
    }
    private Words wrongLetter (String word, Words last){
        StringBuilder nw = new StringBuilder();
        char l;
        int index = 0;
        Words currWord = last;
        for(int i = 0; i < word.length(); ++i){
            for(int j = 0; j < 26; j++) {
                l = (char) ('a' + j);
                nw.append(word);
                nw.setCharAt(i, l);
                INode t = trie.find(nw.toString());
                if(t != null) index = t.getValue();
                Words aWord = new Words(nw.toString(), index);
                currWord.setNext(aWord, possibles);
                if(currWord.getNext() != null) currWord = currWord.getNext();
                nw = new StringBuilder();
                index = 0;
            }
        }
        last = currWord;
        return last;
    }
    private Words removeChar(String word, Words last){
        int index = 0;
        Words currWord = last;
        if(word.length() < 2) return last;
        StringBuilder nw = new StringBuilder();
        for(int i = 0; i < word.length(); ++i){
            nw.append(word);
            nw.deleteCharAt(i);
            INode t = trie.find(nw.toString());
            if(t != null) index = t.getValue();
            Words aWord = new Words(nw.toString(), index);
            currWord.setNext(aWord, possibles);
            if(currWord.getNext() != null) currWord = currWord.getNext();
            nw = new StringBuilder();
            index = 0;
        }
        last = currWord;
        return last;
        //for strings that are larger than 1, this removes on char at a time and adds the new word and its possible index from the dictionary
    }
    private Words transposeLetters(String word, Words last){
        int index = 0;
        Words currWord = last;
        if(word.length() < 2) return last;
        StringBuilder nw = new StringBuilder();
        StringBuilder twoChars = new StringBuilder();
        for(int i = 0; i < word.length() - 1; ++i){
            nw.append(word);
            twoChars.append(nw.substring(i,i + 2));
            twoChars.reverse();
            nw.replace(i, i + 2, twoChars.toString());
            INode t = trie.find(nw.toString());
            if(t != null) index = t.getValue();
            Words aWord = new Words(nw.toString(), index);
            currWord.setNext(aWord, possibles);
            if(currWord.getNext() != null) currWord = currWord.getNext();
            nw = new StringBuilder();
            twoChars = new StringBuilder();
            index = 0;
        }//for strings that are larger than 1, this transposes 2 chars at a time and adds the new word and its possible index from the dictionary
        last = currWord;
        return last;
    }
}
