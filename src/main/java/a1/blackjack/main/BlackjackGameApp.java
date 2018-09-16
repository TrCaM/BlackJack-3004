package a1.blackjack.main;

import a1.blackjack.cards.Deck;
import a1.blackjack.commands.Command;
import a1.blackjack.game.Game;
import a1.blackjack.game.GameMode;
import a1.blackjack.interpreters.Interpreter;
import a1.blackjack.views.Console;
import a1.blackjack.views.TextConsole;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class BlackjackGameApp {
  public static void main(String[] args) {
    Console console = new TextConsole();
    // Ask for game mode
    GameMode mode = console.promptGameMode();
    // Initialize the game
    if (mode == GameMode.CONSOLE) {
      Game.initConsoleInputGame(console).start();
    } else {
      try {
        Queue<Command> commandQueue = new LinkedList<>();
        Deck deck = Deck.getEmptyDeck();
        Interpreter.stringInterpret(getInputStringFromFile(), commandQueue, deck);
        Game.initFileInputGame(console, commandQueue, deck).start();
      } catch (NullPointerException e) {
        System.out.println("File inputs.txt not found");
      } catch (IllegalArgumentException e) {
        System.out.println(String.format("Error reading file: %s", e.getMessage()));
      }
    }
    // Finally start the game. Enjoy!!
  }

  private static String getInputStringFromFile() {
    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
    InputStream is = classloader.getResourceAsStream("inputs.txt");
    Scanner scanner = new Scanner(is);
    return scanner.nextLine();
  }
}
