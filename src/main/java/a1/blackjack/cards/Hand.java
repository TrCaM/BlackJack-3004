package a1.blackjack.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a hand of cards.
 */
public class Hand {
  private List<Card> cards;

  public Hand() {
    cards = new ArrayList<>();
  }

  public Hand(Card... cards) {
    this.cards = new ArrayList<>();
    Collections.addAll(this.cards, cards);
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
    int numAce = 0;
    int score = 0;
    for (Card card : cards) {
      int value = card.getValue();
      score += value > 10 ? 10 : value;
      numAce += value == 1 ? 1 : 0;
    }
    while (numAce > 0) {
      if (score < 12) {
        score+= 10;
      } else if (score < 13) {
        score+= 9;
      }
      numAce -= 1;
    }

    return score > 21 ? 0 : score;
  }

  /**
   * Check if the hand is empty
   */
  public boolean isEmpty() {
    return cards.isEmpty();
  }

  public List<Card> getCards() {
    return cards;
  }
}
