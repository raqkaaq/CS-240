package hangman;

public class GuessAlreadyMadeException extends Exception {
    char guess;
    GuessAlreadyMadeException(char guess){
        this.guess = guess;
    }
    @Override
    public String getMessage(){
        return "You already guessed " + guess;
    }
}
