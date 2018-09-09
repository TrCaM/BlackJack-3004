package a1.blackjack.cards;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CardTest {
  @Test(expected =  IllegalArgumentException.class)
  public void createCard_invalid_shouldThrow() {
    new Card(Suit.HEART, 15);
  }

  @Test
  public void createCard_faceDown() {
    Card card = new Card(Suit.HEART, 1);
    assertTrue(!card.isUp());
  }
}