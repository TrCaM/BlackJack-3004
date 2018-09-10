package a1.blackjack.commands;

import a1.blackjack.cards.Card;
import a1.blackjack.players.Player;

import java.util.List;

/**
 * The source of commands to make players'moves.
 */
public interface CommandEngine {
  /**
   * Get the next {@link Command} to be executed by the player.
   * @return the command to be executed
   */
  public Command getNextCommand();

  /**
   * Set up the CommandEngine with the player for use
   */
  public void setup(Player player);
}