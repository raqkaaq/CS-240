package hangman;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class EvilHangman {
    public static void main(String[] args) throws IOException, EmptyDictionaryException, GuessAlreadyMadeException {
        File dictionary = new File(args[0]);
        int wordCount = Integer.parseInt(args[1]);
        int guesses = Integer.parseInt(args[2]);
        EvilHangmanGame evil = new EvilHangmanGame();
        evil.startGame(dictionary, wordCount);
        while(guesses > 0) {
            System.out.println("You have " + guesses + " guesses left");
            System.out.print("Used letters:");
            if (!evil.getGuessedLetters().isEmpty()) {
                Iterator<Character> it = evil.getGuessedLetters().iterator();
                while (it.hasNext()) {
                    System.out.print(" " + it.next());
                }
            }
            System.out.print('\n');
            System.out.println("Word: " + evil.getWord());
            if(evil.getWord().indexOf('-') == -1){
                System.out.println("You won the game!!");
                break;
            }
            System.out.print("\nEnter guess: ");
            Scanner scan = new Scanner(System.in);
            char guess = scan.next().charAt(0);
            if (evil.isLetter(guess)) {
                try {
                    evil.makeGuess(Character.toLowerCase(guess));
                    if(!evil.isCorrectGuess())
                        guesses -= 1;
                } catch (GuessAlreadyMadeException e){
                    System.out.println("Guess already made");
                }
            } else {
                System.out.println("Invalid Input");
                continue;
            }
            if(guesses == 0 && evil.getWord().indexOf('-') != -1){
                System.out.println("You lose!");
                System.out.println("The word was " + evil.getDictionary().iterator().next() + "!");
            }
        }
    }
}


