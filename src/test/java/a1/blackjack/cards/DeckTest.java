package a1.blackjack.cards;

import org.junit.Test;

import static org.junit.Assert.*;

public class DeckTest {

  @Test(expected = UnsupportedOperationException.class)
  public void getFullDeck() {
    Deck.getFullDeck();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void draw() {
    Deck deck = Deck.getEmptyDeck();
    deck.draw();
  }

  @Test(expected = UnsupportedOperationException.class)
  public void shuffle() {
    Deck deck = Deck.getEmptyDeck();
    deck.shuffle();
  }
}