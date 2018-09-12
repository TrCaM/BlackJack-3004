package a1.blackjack.cards;

import java.util.ArrayList;
import java.util.Collections;
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
   * Form a start with the specified cards
   */
  public static Deck getDeck(List<Card> cards) {
     Deck deck = new Deck();
     deck.cards.addAll(cards);
     return deck;
  }

  /**
   * Get a full deck of 52 cards.
   */
  public static Deck getFullDeck() {
    Deck deck = new Deck();
    for (Suit suit : Suit.values()) {
      for (int value = 1; value <= 13; value++) {
        deck.cards.add(new Card(suit, value));
      }
    }
    return deck;
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
    return cards.isEmpty() ? null : cards.remove(cards.size() -1);
  }

  /**
   * Shuffle randomly the deck
   */
  public void shuffle() {
    Collections.shuffle(cards);
  }

  public List<Card> getCards() {
    return this.cards;
  }

}
