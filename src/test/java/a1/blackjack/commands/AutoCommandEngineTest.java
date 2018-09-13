package a1.blackjack.commands;

import a1.blackjack.cards.Card;
import a1.blackjack.cards.Hand;
import a1.blackjack.cards.Suit;
import a1.blackjack.game.Game;
import a1.blackjack.players.Player;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.when;

public class AutoCommandEngineTest {
  private static final Card S8 = new Card(Suit.SPADE, 8);
  private static final Card H5 = new Card(Suit.HEART, 5);
  private static final Card C3 = new Card(Suit.CLUB, 3);
  private static final Card D7 = new Card(Suit.SPADE, 7);
  private static final Card H7 = new Card(Suit.HEART, 7);
  private static final Card DA = new Card(Suit.DIAMOND, 1);
  private static final Card SK = new Card(Suit.SPADE, 13);
  private static final Card CJ = new Card(Suit.CLUB, 11);
  private static final Card CK = new Card(Suit.CLUB, 13);
  private static final Card HQ = new Card(Suit.HEART, 12);

  @Mock
  private Player player;

  @Mock
  private Game game;

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  private CommandEngine commandEngine;

  @Before
  public void setUp() throws Exception {
    when(game.getActivePlayer()).thenReturn(player);
    commandEngine = new AutoCommandEngine(game);
  }

  @Test
  public void getNextCommand_handLessThan16_shouldHit() {
    Hand hand = new Hand(S8, H5);
    when(player.getPlayingHand()).thenReturn(hand);


  }
}