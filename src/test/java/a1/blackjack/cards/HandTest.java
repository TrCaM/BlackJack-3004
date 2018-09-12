package a1.blackjack.cards;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HandTest {
  // Some sample cards for testing
  private static final Card S8 = new Card(Suit.SPADE, 8);
  private static final Card H5 = new Card(Suit.HEART, 5);
  private static final Card C3 = new Card(Suit.CLUB, 3);
  private static final Card D7 = new Card(Suit.SPADE, 7);
  private static final Card DA = new Card(Suit.DIAMOND, 1);
  private static final Card SA = new Card(Suit.SPADE, 1);
  private static final Card SK = new Card(Suit.SPADE, 13);
  private static final Card CJ = new Card(Suit.CLUB, 11);
  private static final Card HQ = new Card(Suit.HEART, 12);

  @Test
  public void getHandScore_onlyNumberCards() {
    Hand hand1 = new Hand(S8, H5, D7);
    Hand hand2 = new Hand(C3, D7);
    assertEquals(20, hand1.getHandScore());
    assertEquals(10, hand2.getHandScore());
  }

  @Test
  public void getHandScore_withFaceCards() {
    Hand hand1 = new Hand(H5, CJ);
    Hand hand2 = new Hand(SK, C3);
    Hand hand3 = new Hand(SK, HQ);
    assertEquals(15, hand1.getHandScore());
    assertEquals(13, hand2.getHandScore());
    assertEquals(20, hand3.getHandScore());
  }

  @Test
  public void getHandScore_withAceCards() {
    Hand hand1 = new Hand(DA, S8, D7);
    Hand hand2 = new Hand(DA, SK);
    Hand hand3 = new Hand(DA, SA, D7, C3);
    assertEquals(16, hand1.getHandScore());
    assertEquals(21, hand2.getHandScore());
    assertEquals(21, hand3.getHandScore());
  }

  @Test
  public void getHandScore_busted() {
    Hand hand1 = new Hand(SK, S8, D7);
    Hand hand2 = new Hand(S8, D7, H5, CJ);
    Hand hand3 = new Hand(DA, SA, SK, HQ);
    assertEquals(hand1.getHandScore(), 0);
    assertEquals(hand2.getHandScore(), 0);
    assertEquals(hand3.getHandScore(), 0);
  }
}