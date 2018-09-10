package a1.blackjack.players;

import a1.blackjack.cards.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a hand of cards.
 */
public class Hand {
  private List<Card> cards;

  public Hand() {
    cards = new ArrayList<>();
  }

  /**
   * Add a card in to the hand.
   */
  public void addCard(Card card) {
    cards.add(card);
  }

  /**
   * Compute the highest score that is not bust for the hand:
   *  - If the hand contains aces, it compute the best score that <= 21
   *  - If the hand is busted, return 0
   * @return the score the hand
   */
  public int getHandScore() {
    throw new UnsupportedOperationException();
  }

  /**
   * Check if the hand is empty.
   */
  public boolean isEmpty() {
    return cards.isEmpty();
  }

  public List<Card> getCards() {
    return cards;
  }
}
