package hangman;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame{
    public void setDictionary(Set<String> dictionary) {
        this.dictionary = dictionary;
    }

    public boolean isCorrectGuess() {
        return isCorrectGuess;
    }

    private boolean isCorrectGuess;

    public Set<String> getDictionary() {
        return dictionary;
    }

    private Set<String> dictionary;
    private int wordLength;
    private SortedSet<Character> guesses;
    private String word;
    Map<String, Set<String>> partitions;
    public String getWord() {
        return word;
    }
    boolean isLetter(char a){
        return (a >= 'a' && a <= 'z') || (a >= 'A' && a <= 'Z');
    }
    @Override
    public void startGame(File dictionary, int wordLength) throws IOException, EmptyDictionaryException {
        this.isCorrectGuess = false;
        this.wordLength = wordLength;
        this.guesses = new TreeSet<>();
        String a = "-";
        this.word = a.repeat(wordLength);
        if (dictionary.length() == 0)
            throw new EmptyDictionaryException();
        Scanner scan = new Scanner(dictionary);
        this.dictionary = new HashSet();
        while (scan.hasNext()) {
            String s = scan.next();
            if (s.length() == wordLength) {
                this.dictionary.add(s);
            }
        }
        if(this.dictionary.size() == 0){
            throw new EmptyDictionaryException();
        }
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        this.isCorrectGuess = false;
        this.partitions = new HashMap<>();
        if(guesses.contains(Character.toLowerCase(guess)))
            throw new GuessAlreadyMadeException(guess);
        else guesses.add(Character.toLowerCase(guess));
        for(String word : dictionary){
            String key = createKey(word, guess);
            if(!partitions.containsKey(key)){
                partitions.put(key, new HashSet<>());
            }
            partitions.get(key).add(word);
        }
        int largestSet = 0;
        //find the size of the largest set
        for(String key : partitions.keySet()){
            int size = partitions.get(key).size();
            if(size > largestSet)
                largestSet = size;
        }
        //remove all key,value pairs where the value is less than the largest set
        Iterator it = partitions.keySet().iterator();
        while(it.hasNext()){
            String next = (String) it.next();
            int size = partitions.get(next).size();
            if(size < largestSet){
                it.remove();
            }
        }
        Set<String> priority = getPrioritySet(partitions, guess);
        setDictionary(priority);
        return priority;
    }

    private Set<String> getPrioritySet(Map<String, Set<String>> partitions, char guess){
        Set<String> priority = new HashSet<>();
        int minWeight = -1;
        String maybeKey = "";
        for(String key : partitions.keySet()){
             int weight = 1;
             int count = 0;
             StringBuilder tempKey = new StringBuilder(key);
             //gets count of chars same as guess in key
             count = (int)tempKey.chars().filter(ch -> ch == guess).count();
             if(count != 0){
                 isCorrectGuess = true;
             }
             int index = tempKey.toString().indexOf(guess);
             while(index != -1){
                 tempKey.setCharAt(index, '*');
                 weight += weight + (wordLength - index + 1);
                 index = tempKey.toString().indexOf(guess);
             }
             weight *= count;
             if(weight < minWeight || minWeight == -1){
                 minWeight = weight;
                 maybeKey = key;
             }
        }
        priority = partitions.get(maybeKey);
        this.word = maybeKey;
        return priority;
    }
    private String createKey(String word, char guess){
        StringBuilder key = new StringBuilder();
        for(int i = 0; i < wordLength; i++){
            char c = word.charAt(i);
            if(c == guess)
                key.append(c);
            else
                key.append(this.word.charAt(i));
        }
        return key.toString();
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return guesses;
    }
}
