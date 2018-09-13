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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AutoCommandEngineTest {
  private static final Card S8 = new Card(Suit.SPADE, 8);
  private static final Card H5 = new Card(Suit.HEART, 5);
  private static final Card S5 = new Card(Suit.SPADE, 5);
  private static final Card D7 = new Card(Suit.SPADE, 7);
  private static final Card H6 = new Card(Suit.HEART, 6);
  private static final Card DA = new Card(Suit.DIAMOND, 1);
  private static final Card SK = new Card(Suit.SPADE, 13);
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
  public void setUp() {
    when(game.getActivePlayer()).thenReturn(player);
    commandEngine = new AutoCommandEngine();
  }

  @Test
  public void getNextCommand_handLessThan16_shouldHit() {
    Hand hand = new Hand(S8, H5);
    when(player.getPlayingHand()).thenReturn(hand);

    Command command = commandEngine.getNextCommand(game);

    assertThat(command, is(Command.HIT));
    verify(game).getActivePlayer();
    verify(player).getPlayingHand();
  }

  @Test
  public void getNextCommand_soft17_shouldHit() {
    Hand hand = new Hand(DA, H6);
    when(player.getPlayingHand()).thenReturn(hand);

    Command command = commandEngine.getNextCommand(game);

    assertThat(command, is(Command.HIT));
    verify(game).getActivePlayer();
    verify(player).getPlayingHand();
  }

  @Test
  public void getNextCommand_17_shouldStand() {
    Hand hand = new Hand(CK, D7);
    when(player.getPlayingHand()).thenReturn(hand);

    Command command = commandEngine.getNextCommand(game);

    assertThat(command, is(Command.STAND));
    verify(game).getActivePlayer();
    verify(player).getPlayingHand();
  }

  @Test
  public void getNextCommand_20_shouldStand() {
    Hand hand = new Hand(CK, HQ);
    when(player.getPlayingHand()).thenReturn(hand);

    Command command = commandEngine.getNextCommand(game);

    assertThat(command, is(Command.STAND));
    verify(game).getActivePlayer();
    verify(player).getPlayingHand();
  }

  @Test
  public void getNextCommand_identicalHandOver16_shouldStand() {
    Hand hand = new Hand(CK, SK);
    when(player.getPlayingHand()).thenReturn(hand);

    Command command = commandEngine.getNextCommand(game);

    assertThat(command, is(Command.STAND));
    verify(game).getActivePlayer();
    verify(player).getPlayingHand();
  }

  @Test
  public void getNextCommand_identicalHandLess16_shouldSplit() {
    Hand hand = new Hand(S5, H5);
    when(player.getPlayingHand()).thenReturn(hand);

    Command command = commandEngine.getNextCommand(game);

    assertThat(command, is(Command.SPLIT));
    verify(game).getActivePlayer();
    verify(player).getPlayingHand();
  }
}