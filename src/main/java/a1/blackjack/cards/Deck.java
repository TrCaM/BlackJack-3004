package a1.blackjack.cards;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class for a deck, which is a stack of cards.
 */
public class Deck {
  private List<Card> cards;

  private Deck() {
    cards = new ArrayList<Card>();
  }

  public static Deck getEmptyDeck() {
    return new Deck();
  }

  /**
   * Get a full deck of 52 cards.
   */
  public static Deck getFullDeck() {
    throw new UnsupportedOperationException();
  }

  /**
   * Form a start with the specified cards
   */
  public static Deck getDeck(List<Card> cards) {
    throw new UnsupportedOperationException();
  }

  /**
   * Get the number of cards in the deck
   */
  public int size() {
    return cards.size();
  }

  /**
   * Draw the card on top
   */
  public Card draw() {
    throw new UnsupportedOperationException();
  }

  /**
   * Shuffle randomly the deck
   */
  public void shuffle() {
    throw new UnsupportedOperationException();
  }

  public List<Card> getCards() {
    return this.cards;
  }

}
