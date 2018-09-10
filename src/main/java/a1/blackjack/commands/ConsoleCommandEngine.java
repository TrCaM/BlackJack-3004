package a1.blackjack.commands;

import a1.blackjack.players.Player;

/**
 * Produces command using the console or file input.
 */
public class ConsoleCommandEngine implements CommandEngine {
  @Override
  public Command getNextCommand() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setup(Player player) {
    //Do nothing as it does not need any information from game state.
  }
}
