package hw2;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * Text-based user interface for the WheelOfFortuneGame.
 */
public class GameTextUI
{
  /**
   * Scanner to read from the console.
   */
  private Scanner scanner = new Scanner(System.in);
  
  /**
   * The game.
   */
  private Game game;
  
  /**
   * Source of randomness for wheel spins and secret phrases.
   */
  private Random rand = new Random();
  
  /**
   * Entry point.
   * @param args args
   * @throws FileNotFoundException file not found
   */
  public static void main(String[] args) throws FileNotFoundException
  {
    new GameTextUI().go("phrases.txt");
  }
  
  /**
   * Starts the UI.
   * @param filename name of file to read
   * @throws FileNotFoundException file not found
   */
  public void go(String filename) throws FileNotFoundException
  {   
    PhraseList generator = new PhraseList(filename);
    String[] names = {"Moony", "Padfoot", "Prongs"};
    
//    String[] names = new String[3];
//    Scanner scan = new Scanner(System.in);
//    
//    for(int i=0; i<3; i++)
//    {
//    	System.out.println("Enter name of player "+ (i+1));
//    	names[i] = scan.next();
//    }
//    scan.close(); 
       
    
    game = new Game(names);  
    int playerToStart = 0;
    boolean over = false;
    
    System.out.println("CS 227 Wheel of Fortune");
    System.out.println("-----------------------");
    printFinalStatus();
    System.out.println();
    
    while (!over)
    {
      String phrase = generator.getPhrase(rand.nextInt(generator.getSize()));
      game.startRound(playerToStart, new HiddenText(phrase));
      while (!game.roundOver())
      {
        printCurrentStatus();
        if (game.needsSpin())
        {
          spin();
        }
        if (game.getWheelValue() != Wheel.BANKRUPT && 
            game.getWheelValue() != Wheel.LOSE_A_TURN)
        {
          menu();
          System.out.print("Enter your choice: ");
          String entry = scanner.nextLine().toLowerCase();
          if (entry.startsWith("c"))
          {
            doGuessLetter(false);
          }
          else if (entry.startsWith("b"))
          {
            doGuessLetter(true);
          }
          else if (entry.startsWith("a"))
          {
            doGuessPhrase();
          }
          else
          {
            System.out.println("Please enter a, b, or c");
          }
        }
      }
      System.out.println("That ends this round: ");
      printFinalStatus();
      
      System.out.println();
      System.out.print("Play again (y/n)? ");
      String response = scanner.nextLine().toLowerCase();
      over = response.startsWith("n");
      
      // let next player start
      playerToStart = (game.whoseTurn() + 1) % game.getNumPlayers();
    }
  }
  
  /**
   * Displays the current balances for the round, 
   * the current displayed phrase, and whose turn it is.
   */
  private void printCurrentStatus()
  {
    for (int i = 0; i < game.getNumPlayers(); i += 1)
    {
      System.out.println(game.getPlayerName(i) + ": " + game.getRoundBalance(i));
    }
    System.out.println();
    System.out.println(new String(game.getDisplay()));
    System.out.println();
    System.out.println("It is " + game.getPlayerName(game.whoseTurn()) + "'s turn.");
  }

  /**
   * Displays the overall totals.
   */
  private void printFinalStatus()
  {
    for (int i = 0; i < game.getNumPlayers(); ++i)
    {
      System.out.println(game.getPlayerName(i) + " total winnings: " + game.getGameBalance(i));
    }
  }

  /**
   * Displays the short menu of choices for a player's turn.
   */
  private void menu()
  {
    System.out.println("  a) solve the puzzle");
    if (game.getRoundBalance(game.whoseTurn()) >= Game.VOWEL_COST)
    {
      System.out.println("  b) buy a vowel");  // only display this if player has enough money 
    }
    System.out.println("  c) guess a consonant");
  }
  
  /**
   * Spins the wheel and displays the result.
   */
  private void spin()
  {
    System.out.println("Press ENTER to spin the wheel");
    scanner.nextLine();
    int spin = rand.nextInt(360) + 180; // spin at least 180
    game.spinWheel(spin);
    int result = game.getWheelValue();
    String outcome;
    if (result == Wheel.BANKRUPT)
    {
      outcome = "BANKRUPT!";
    }
    else if (result == Wheel.LOSE_A_TURN)
    {
      outcome = "Lose a Turn";
    }
    else
    {
      outcome = "$" + result;
    }
    System.out.println("You spun: " + outcome + " (" + game.getWheelRotation() + " degrees)");
  }
  
  /**
   * Handles the user's choice to guess a consonant or buy
   * a vowel.
   * @param buyingAVowel
   *   true if the user is buying a vowel
   */
  private void doGuessLetter(boolean buyingAVowel)
  {
    char guess;
    int result;
    if (buyingAVowel)
    {
      System.out.print("Enter a vowel: ");
      guess = scanner.nextLine().toUpperCase().charAt(0);
      result = game.buyVowel(guess);
    }
    else
    {
      System.out.print("Enter a consonant: ");
      guess = scanner.nextLine().toUpperCase().charAt(0);
      result = game.guessConsonant(guess);
    }
    if (result == 1)
    {
      System.out.println("There was one " + guess);
    }
    else if (result > 1)
    {
      System.out.println("There were " + result + " " + guess + "'s");        
    }
    else
    {
      System.out.println("Sorry, there were no " + guess + "'s");
    }
    System.out.println();
  }
  
  /**
   * Handles the user's choice to solve the puzzle.
   */
  private void doGuessPhrase()
  {
    System.out.print("Enter your guess: ");
    String guess = scanner.nextLine();
    boolean correct = game.guessPhrase(guess);
    if (correct)
    {
      System.out.println("That's it!");
    }
    else
    {
      System.out.println("Sorry, that wasn't it.");
    }
    System.out.println();
  }
}
