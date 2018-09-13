package a1.blackjack.game;

import a1.blackjack.cards.Deck;
import a1.blackjack.commands.Command;
import a1.blackjack.players.Player;

/**
 * {@link Game} class is responsible for or the game logic.
 */
public class Game {
  private Player player;
  private Player dealer;
  private Deck deck;
  private boolean isPlayerTurn;

  public Player getActivePlayer() {
    return isPlayerTurn ? player : dealer;
  }

  private void initGame() {
    throw new UnsupportedOperationException();
  }

  private void playTurn(Player player) {

  }

  private void validateStateForEvent() {
    throw new UnsupportedOperationException();
  }

  private Command getCommand() {
    throw new UnsupportedOperationException();
  }

  private void executeCommand() {
    throw new UnsupportedOperationException();
  }
}
