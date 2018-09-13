package a1.blackjack.commands;

import a1.blackjack.game.Game;

/**
 * Automatically generate commands base on the current player's hand. It needs to access the state
 * of the game, specifically all the hands of the player who uses this engine to make the next
 * command.
 */
public class AutoCommandEngine implements CommandEngine{
  private Game game;

  public AutoCommandEngine(Game game) {
    this.game = game;
  }

  @Override
  public Command getNextCommand() {
    return null;
  }
}
