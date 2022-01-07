package spell;

import java.io.IOException;

/**
 * A simple main class for running the spelling corrector. This class is not
 * used by the passoff program.
 */
public class Main {
	static Trie a = new Trie();
	static Trie b = new Trie();
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws IOException {
		
		String dictionaryFileName = args[0];
		String inputWord = args[1];
		//
        //Create an instance of your corrector here
        //
		ISpellCorrector corrector = new SpellCorrector();
		
		corrector.useDictionary(dictionaryFileName);
		String suggestion = corrector.suggestSimilarWord(inputWord);
		if (suggestion == null) {
		    suggestion = "No similar word found";
		}
		System.out.println("Suggestion is: " + suggestion);
		//a.add("dat");
		//b.add("far");
		//System.out.println(a.hashCode() == b.hashCode());
		//add("cares");
		//add("caress");
		//add("baboon");
		a.add("car");
		b.add("car");
		b.add("car");
		System.out.println("l  " + a.equals(b));
	}
	private static void add(String word){
		a.add(word);
		b.add(word);
	}

}
