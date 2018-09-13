package a1.blackjack.game;

import a1.blackjack.cards.Deck;
import a1.blackjack.cards.Hand;
import a1.blackjack.players.Player;

/**
 * Storage for all the information needed to operate the game
 */
public class GameState {
  private Hand activeHand;
  private Player player;
  private Player dealer;
  private Deck deck;

  static GameState initGameState() {
    throw new UnsupportedOperationException();
  }

  public void setActiveHand(Hand activeHand) {
    this.activeHand = activeHand;
  }

  public Hand getActiveHand() {
    return activeHand;
  }
}
