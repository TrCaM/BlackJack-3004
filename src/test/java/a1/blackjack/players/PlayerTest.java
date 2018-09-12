package a1.blackjack.players;

import a1.blackjack.cards.Card;
import a1.blackjack.cards.Deck;
import a1.blackjack.cards.Suit;
import a1.blackjack.commands.CommandEngine;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.List;

public class PlayerTest {
  private static final Card S8 = new Card(Suit.SPADE, 8);
  private static final Card H5 = new Card(Suit.HEART, 5);
  private static final Card C3 = new Card(Suit.CLUB, 3);
  private static final Card D7 = new Card(Suit.SPADE, 7);
  private static final Card DA = new Card(Suit.DIAMOND, 1);
  private static final Card SA = new Card(Suit.SPADE, 1);
  private static final Card SK = new Card(Suit.SPADE, 13);
  private static final Card CJ = new Card(Suit.CLUB, 11);
  private static final Card HQ = new Card(Suit.HEART, 12);
  private static final List<Card> cards = Arrays.asList(S8, H5, C3, D7, DA, SA, SK, CJ, HQ);

  private Deck deck;
  private Player player;

  @Mock
  CommandEngine commandEngine;

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Before
  public void setUp() {
     deck = Deck.getDeck(cards);
     player = new Player(commandEngine);
  }

  @Test
  public void hit_shouldSucceed() {

  }

  @Test
  public void stand() {
  }

  @Test
  public void split() {
  }
}