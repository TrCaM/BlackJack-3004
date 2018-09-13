package a1.blackjack.commands;

import a1.blackjack.cards.Card;
import a1.blackjack.cards.Hand;
import a1.blackjack.cards.Suit;
import a1.blackjack.game.Game;
import a1.blackjack.players.Player;
import a1.blackjack.views.Console;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.LinkedList;
import java.util.Queue;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class ConsoleCommandEngineTest {
  private static final Card S8 = new Card(Suit.SPADE, 8);
  private static final Card H5 = new Card(Suit.HEART, 5);
  private static final Card S5 = new Card(Suit.SPADE, 5);

  @Mock
  private Player player;

  @Mock
  private Game game;

  @Mock
  private Console console;

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  private CommandEngine commandEngine;

  @Before
  public void setUp() {
  }

  @Test
  public void getNextCommand_usingFile_shouldSucceed() {
    Hand hand = new Hand(S8, H5);
    when(game.getActivePlayer()).thenReturn(player).thenReturn(player);
    when(player.getPlayingHand()).thenReturn(hand).thenReturn(hand);
    Queue<Command> commandQueue = new LinkedList<>();
    commandQueue.add(Command.HIT);
    commandQueue.add(Command.STAND);
    commandEngine = new ConsoleCommandEngine(console, commandQueue);

    Command chosenCommand1 = commandEngine.getNextCommand(game);
    Command chosenCommand2 = commandEngine.getNextCommand(game);

    assertThat(chosenCommand1, is(Command.HIT));
    assertThat(chosenCommand2, is(Command.STAND));
    verify(game, times(2)).getActivePlayer();
    verify(player, times(2)).getPlayingHand();
    verifyZeroInteractions(console);
  }

  @Test(expected = IllegalStateException.class)
  public void getNextCommand_usingFile_queueError() {
    Hand hand = new Hand(S8, H5);
    when(game.getActivePlayer()).thenReturn(player);
    when(player.getPlayingHand()).thenReturn(hand);
    Queue<Command> commandQueue = new LinkedList<>();
    commandEngine = new ConsoleCommandEngine(console, commandQueue);

    commandEngine.getNextCommand(game);
  }

  @Test
  public void getNextCommand_consoleInput_shouldSucceed() {
    Hand hand = new Hand(S5, H5);
    when(game.getActivePlayer()).thenReturn(player);
    when(player.getPlayingHand()).thenReturn(hand);
    when(console.promptCommand()).thenReturn(Command.SPLIT);
    commandEngine = new ConsoleCommandEngine(console);

    Command chosenCommand1 = commandEngine.getNextCommand(game);

    assertThat(chosenCommand1, is(Command.SPLIT));
    verify(game).getActivePlayer();
    verify(player).getPlayingHand();
    verify(console).promptCommand();
  }

  @Test(expected = IllegalStateException.class)
  public void getNextCommand_chosenCommandNotAvailable_shouldThrow() {
    Hand hand = new Hand(S8, H5);
    when(game.getActivePlayer()).thenReturn(player);
    when(player.getPlayingHand()).thenReturn(hand);
    when(console.promptCommand()).thenReturn(Command.SPLIT);
    commandEngine = new ConsoleCommandEngine(console);

    commandEngine.getNextCommand(game);
  }
}