package a1.blackjack.game;

import a1.blackjack.cards.Card;
import a1.blackjack.cards.Deck;
import a1.blackjack.cards.Hand;
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

  private void beforeDealerTurn() {
    dealer.getPlayingHand().getCards().forEach(Card::faceUp);
    isPlayerTurn = false;
    showPlayersHands();
  }

  private void showPlayersHands() {
    console.notify("---------------------------------------");
    showPlayerHands(player);
    showPlayerHands(dealer);
    console.notify("---------------------------------------");
  }

  private void showPlayerHands(Player player) {
    console.notify(String.format("%s's hand contains: ", player.getName()));
    console.notify("\t- Main hand contains:");
    displayHand(player.getMainHand());
    if (player.isSplitting()) {
      console.notify("\t- Second hand contains:");
      displayHand(player.getSplitHand());
    }
  }

  private void displayHand(Hand hand) {
    StringBuilder sb = new StringBuilder();
    hand.getCards().forEach(card -> sb.append("\t\t+ ").append(card).append("\n"));
    console.notify(sb.toString());
  }

  private void showPlayersScore() {
    showPlayerScore(player);
    showPlayerScore(dealer);
  }

  private void showPlayerScore(Player player) {
    StringBuilder sb = new StringBuilder();
    if (player.isBusted()) {
      sb.append(player.getName()).append(" is busted.");
    } else {
      sb.append(player.getName()).append("'s score is ").append(player.getScore());
    }
    console.notify(sb.toString());
  }
}
