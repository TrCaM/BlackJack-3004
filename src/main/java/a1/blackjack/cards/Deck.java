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

  public static Deck getFullDeck() {
    throw new UnsupportedOperationException();
  }

  public Card draw() {
    throw new UnsupportedOperationException();
  }

  public void shuffle() {
    throw new UnsupportedOperationException();
  }
}
