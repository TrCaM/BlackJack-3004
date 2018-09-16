package a1.blackjack.views;

import a1.blackjack.commands.Command;
import a1.blackjack.game.GameMode;

import java.util.Set;

/**
 * Interface for the view of the game (text or graphics).
 */
public interface Console {
  /**
   * Get the game mode for initializing the game.
   */
  GameMode promptGameMode();
  /**
   * From the user to input command from the available commands.
   */
  Command promptCommand(Set<Command> availableCommands);

  /**
   * Notify user a message.
   */
  void notify(String message);
}
