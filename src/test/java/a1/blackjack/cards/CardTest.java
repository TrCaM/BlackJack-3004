package a1.blackjack.cards;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
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

  @Test
  public void toString_shouldSucceed() {
    assertThat(new Card(Suit.SPADE, 3).toString(), is("S3"));
    assertThat(new Card(Suit.SPADE, 1).toString(), is("SA"));
    assertThat(new Card(Suit.HEART, 9).toString(), is("H9"));
    assertThat(new Card(Suit.HEART, 10).toString(), is("H10"));
    assertThat(new Card(Suit.DIAMOND, 5).toString(), is("D5"));
    assertThat(new Card(Suit.DIAMOND, 11).toString(), is("DJ"));
    assertThat(new Card(Suit.CLUB, 13).toString(), is("SK"));
    assertThat(new Card(Suit.CLUB, 12).toString(), is("SQ"));
  }
}