package a1.blackjack.game;

import a1.blackjack.cards.Card;
import a1.blackjack.cards.Deck;
import a1.blackjack.players.Player;
import a1.blackjack.views.Console;

/**
 * {@link Game} class is responsible for or the game logic.
 */
public class Game {
  private Player player;
  private Player dealer;
  private Deck deck;
  private boolean isPlayerTurn;

  private Console console;

  private Game(Console console) {
    this.console = console;
  }

  public Player getActivePlayer() {
    return isPlayerTurn ? player : dealer;
  }

  Player getPlayer() {
    return player;
  }

  Player getDealer() {
    return dealer;
  }

  void beforePlayerTurn() {
    // Reveal player hand.
    player.getPlayingHand().getCards().forEach(Card::faceUp);
    // Reveal only 1 card of dealer's hand
    dealer.getPlayingHand().getCards().get(0).faceUp();
    showPlayersHands();
    isPlayerTurn = true;
  }
}
