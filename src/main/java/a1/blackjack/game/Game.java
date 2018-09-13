package a1.blackjack.game;

import a1.blackjack.players.Player;

/**
 * {@link Game} class is responsible for or the game logic.
 */
public class Game {
  private Player player;
  private Player dealer;
  private boolean isPlayerTurn;

  public Player getActivePlayer() {
    return isPlayerTurn ? player : dealer;
  }

}
