package a1.blackjack.commands;

import a1.blackjack.game.GameState;

/**
 * Automatically generate commands base on the current player's hand. It needs to access the state
 * of the game, specifically all the hands of the player who uses this engine to make the next
 * command.
 */
public class AutoCommandEngine implements CommandEngine{
  private GameState gameState;

  public AutoCommandEngine(GameState state) {
    this.gameState = state;
  }

  @Override
  public Command getNextCommand() {
    return null;
  }
}
