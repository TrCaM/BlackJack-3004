package a1.blackjack.views;

import a1.blackjack.commands.Command;

import java.util.Set;

/**
 * Interface for the view of the game (text or graphics).
 */
public interface Console {
  /**
   * From the user to input command from the available commands.
   */
  Command promptCommand(Set<Command> availableCommands);

  /**
   * Notify through interface that the game is starting.
   */
  void startGame();

  /**
   * Notify user a message.
   */
  void notify(String message);
}
