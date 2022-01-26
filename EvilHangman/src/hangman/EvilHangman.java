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
            guesses -= 1;
            System.out.print("Used letters:");
            if (!evil.getGuessedLetters().isEmpty()) {
                Iterator<Character> it = evil.getGuessedLetters().iterator();
                while (it.hasNext()) {
                    System.out.print(" " + it.next());
                }
            }
            System.out.print('\n');
            System.out.println("Word: " + evil.getWord());
            System.out.print("Enter guess: ");
            Scanner scan = new Scanner(System.in);
            char guess = scan.next().charAt(0);
            if (evil.isLetter(guess)) {
                evil.makeGuess(Character.toLowerCase(guess));
                System.out.println("This is the answer spot\n");
            } else {
                System.out.println("Invalid Input");
                continue;
            }
        }
    }
}


