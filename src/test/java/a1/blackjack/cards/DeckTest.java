package a1.blackjack.cards;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.junit.Assert.*;

public class DeckTest {

  private static final Card D5 = new Card(Suit.DIAMOND, 5);
  private static final Card S6 = new Card(Suit.SPADE, 6);
  private static final Card DK = new Card(Suit.DIAMOND, 13);
  private static final Card CA = new Card(Suit.CLUB, 1);
  private static final Card H10= new Card(Suit.HEART, 10);
  private static final List<Card> SAMPLE_CARDS = Arrays.asList(D5, S6, DK, CA, H10);

  @Test
  public void getDeck_shouldSucceed() {
     Deck deck = Deck.getDeck(SAMPLE_CARDS);
     assertThat(deck.getCards(), is(SAMPLE_CARDS));
  }

  @Test
  public void draw_getTopCard() {
    Deck deck = Deck.getDeck(SAMPLE_CARDS);
    assertEquals(deck.draw(), H10);
  }

  @Test
  public void draw_emptyDeck_returnNull() {
    Deck deck = Deck.getEmptyDeck();
    assertNull(deck.draw());
  }

  @Test
  public void getFullDeck_shouldHave52Card() {
    Deck deck = Deck.getFullDeck();
    assertEquals(deck.getCards().size(), 52);
  }

  @Test
  public void shuffle_shouldSucceed() {
    Deck deck = Deck.getDeck(SAMPLE_CARDS);
    deck.shuffle();
    deck.getCards().forEach(Card::faceUp);
    for (Card card : deck.getCards()) {
      assertThat(card, isIn(SAMPLE_CARDS));
    }
  }
}
