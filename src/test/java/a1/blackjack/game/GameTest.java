package a1.blackjack.game;

import a1.blackjack.cards.Card;
import a1.blackjack.cards.Deck;
import a1.blackjack.cards.Suit;
import a1.blackjack.commands.Command;
import a1.blackjack.players.Player;
import a1.blackjack.players.PlayerMode;
import a1.blackjack.views.TextConsole;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GameTest {
  private static final Card C3 = new Card(Suit.CLUB, 3);
  private static final Card DJ = new Card(Suit.DIAMOND, 11);
  private static final Card C5 = new Card(Suit.CLUB, 5);
  private static final Card SQ = new Card(Suit.SPADE, 12);
  private static final Card SK = new Card(Suit.SPADE, 13);
  private static final Card D7 = new Card(Suit.SPADE, 7);
  private static final Card H7 = new Card(Suit.HEART, 7);
  private static final Card CJ = new Card(Suit.CLUB, 11);
  private static final Card HQ = new Card(Suit.HEART, 12);
  private static Deck deck;

  private Game game;

  @Before
  public void setUp() {
    deck = Deck.getDeck(Arrays.asList(DJ, C5, SQ, HQ, SK));
    Queue<Command> commandQueue = new LinkedList<>();
    commandQueue.add(Command.STAND);
    commandQueue.add(Command.HIT);
    deck.getCards().forEach(Card::faceDown);
    game = Game.initFileInputGame(new TextConsole(), commandQueue, deck);
  }

  @Test
  public void beforePlayerTurn_cardsOpenCorrectly() {
    Player player = game.getPlayer();
    Player dealer = game.getDealer();

    for (int i=0; i<2; i++) {
      player.draw(deck, false);
      dealer.draw(deck, false);
    }

    game.beforePlayerTurn();

    for(Card card : player.getMainHand().getCards()) {
      assertThat(card.isUp(), is(true));
    }
    assertThat(dealer.getMainHand().getCards().get(0).isUp(), is(true));
    assertThat(dealer.getMainHand().getCards().get(1).isUp(), is(false));
  }

  @Test
  public void beforeDealerTurn_cardsOpenCorrectly() {
    Player player = game.getPlayer();
    Player dealer = game.getDealer();
    Deck deck = Deck.getDeck(Arrays.asList(C3, D7, H7, CJ, HQ));

    for (int i=0; i<2; i++) {
      player.draw(deck, false);
      dealer.draw(deck, false);
    }

    game.beforeDealerTurn();

    for(Card card : dealer.getMainHand().getCards()) {
      assertThat(card.isUp(), is(true));
    }
  }

  @Test
  public void playTurn_testUserInputAndAutomateCommandEngine_doNothing() {
    Player player = game.getPlayer();
    Player dealer = game.getDealer();

    for (int i=0; i<2; i++) {
      player.draw(deck, false);
    }
    for (int i=0; i<2; i++) {
      dealer.draw(deck, false);
    }

    game.playTurn(player);
    game.playTurn(dealer);

    assertThat(player.getMode(), is(PlayerMode.STANDING));
    assertThat(player.getScore(), is(20));
    assertThat(dealer.getScore(), is(0));
  }

  @Test
  public void playTurn_playersSplitHand() {
    deck = Deck.getDeck(Arrays.asList(
        new Card(Suit.DIAMOND, 5),
        new Card(Suit.CLUB, 5),
        new Card(Suit.DIAMOND, 3),
        new Card(Suit.HEART, 6),
        new Card(Suit.DIAMOND, 9),
        new Card(Suit.CLUB, 12),
        new Card(Suit.HEART, 13),
        new Card(Suit.SPADE, 13)
    ));
    Queue<Command> commandQueue = new LinkedList<>();
    commandQueue.add(Command.SPLIT);
    commandQueue.add(Command.HIT);
    commandQueue.add(Command.STAND);
    commandQueue.add(Command.HIT);
    commandQueue.add(Command.STAND);
    deck.getCards().forEach(Card::faceDown);
    game = Game.initFileInputGame(new TextConsole(), commandQueue, deck);
    Player player = game.getPlayer();
    Player dealer = game.getDealer();

    for (int i=0; i<2; i++) {
      player.draw(deck, true);
    }
    for (int i=0; i<2; i++) {
      dealer.draw(deck, true);
    }

    game.playTurn(player);
    game.playTurn(dealer);

    assertThat(player.getMode(), is(PlayerMode.STANDING));
    assertThat(dealer.getMode(), is(PlayerMode.STANDING));
    assertThat(player.getScore(), is(20));
    assertThat(dealer.getScore(), is(19));
    assertThat(game.getWinner(), is(player));
  }

  @Test
  public void playTurn_dealerSplitHand() {
    deck = Deck.getDeck(Arrays.asList(
        new Card(Suit.DIAMOND, 2),
        new Card(Suit.SPADE, 12),
        new Card(Suit.SPADE, 1),
        new Card(Suit.CLUB, 12),
        new Card(Suit.HEART, 7),
        new Card(Suit.DIAMOND, 5),
        new Card(Suit.CLUB, 5),
        new Card(Suit.HEART, 9),
        new Card(Suit.SPADE, 13)
    ));
    Queue<Command> commandQueue = new LinkedList<>();
    commandQueue.add(Command.STAND);
    deck.getCards().forEach(Card::faceDown);
    game = Game.initFileInputGame(new TextConsole(), commandQueue, deck);
    Player player = game.getPlayer();
    Player dealer = game.getDealer();

    for (int i=0; i<2; i++) {
      player.draw(deck, true);
    }
    for (int i=0; i<2; i++) {
      dealer.draw(deck, true);
    }

    game.playTurn(player);
    game.playTurn(dealer);

    assertThat(player.getMode(), is(PlayerMode.STANDING));
    assertThat(dealer.getMode(), is(PlayerMode.STANDING));
    assertThat(player.getScore(), is(19));
    assertThat(dealer.getScore(), is(18));
    assertThat(game.getWinner(), is(player));
  }

  @Test
  public void gameTest_blackJackForBoth_dealerWinsWithoutPlaying() {
    deck = Deck.getDeck(Arrays.asList(
        new Card(Suit.CLUB, 1),
        new Card(Suit.HEART, 12),
        new Card(Suit.HEART, 1),
        new Card(Suit.SPADE, 13)
    ));
    Queue<Command> commandQueue = new LinkedList<>();
    deck.getCards().forEach(Card::faceDown);
    game = Game.initFileInputGame(new TextConsole(), commandQueue, deck);

    game.start();

    Player player = game.getPlayer();
    Player dealer = game.getDealer();
    assertThat(player.getMode(), is(PlayerMode.STANDING));
    assertThat(dealer.getMode(), is(PlayerMode.STANDING));
    assertThat(player.getScore(), is(21));
    assertThat(dealer.getScore(), is(21));
    assertThat(game.getWinner(), is(dealer));
  }

  @Test
  public void gameTest_blackJackForPlayer_playerWinNoPlay() {
    deck = Deck.getDeck(Arrays.asList(
        new Card(Suit.CLUB, 4),
        new Card(Suit.HEART, 12),
        new Card(Suit.HEART, 1),
        new Card(Suit.SPADE, 13)
    ));
    Queue<Command> commandQueue = new LinkedList<>();
    deck.getCards().forEach(Card::faceDown);
    game = Game.initFileInputGame(new TextConsole(), commandQueue, deck);

    game.start();

    Player player = game.getPlayer();
    Player dealer = game.getDealer();
    assertThat(player.getMode(), is(PlayerMode.STANDING));
    assertThat(dealer.getMode(), is(PlayerMode.STANDING));
    assertThat(player.getScore(), is(21));
    assertThat(dealer.getScore(), is(14));
    assertThat(game.getWinner(), is(player));
  }
  @Test
  public void gameTest_playerBusts_dealerWins() {
    deck = Deck.getDeck(Arrays.asList(
        new Card(Suit.SPADE, 4),
        new Card(Suit.CLUB, 4),
        new Card(Suit.HEART, 12),
        new Card(Suit.HEART, 10),
        new Card(Suit.SPADE, 13)
    ));
    Queue<Command> commandQueue = new LinkedList<>();
    commandQueue.add(Command.HIT);
    deck.getCards().forEach(Card::faceDown);
    game = Game.initFileInputGame(new TextConsole(), commandQueue, deck);

    game.start();

    Player player = game.getPlayer();
    Player dealer = game.getDealer();
    assertThat(player.getMode(), is(PlayerMode.STANDING));
    assertThat(dealer.getMode(), is(PlayerMode.STANDING));
    assertThat(player.getScore(), is(0));
    assertThat(dealer.getScore(), is(14));
    assertThat(game.getWinner(), is(dealer));
  }

  @Test
  public void gameTest_dealerBusts_playerWins() {
    deck = Deck.getDeck(Arrays.asList(
        new Card(Suit.SPADE, 10),
        new Card(Suit.CLUB, 4),
        new Card(Suit.HEART, 10),
        new Card(Suit.HEART, 12),
        new Card(Suit.SPADE, 13)
    ));
    Queue<Command> commandQueue = new LinkedList<>();
    commandQueue.add(Command.STAND);
    deck.getCards().forEach(Card::faceDown);
    game = Game.initFileInputGame(new TextConsole(), commandQueue, deck);

    game.start();

    Player player = game.getPlayer();
    Player dealer = game.getDealer();
    assertThat(player.getMode(), is(PlayerMode.STANDING));
    assertThat(dealer.getMode(), is(PlayerMode.STANDING));
    assertThat(player.getScore(), is(20));
    assertThat(dealer.getScore(), is(0));
    assertThat(game.getWinner(), is(player));
  }
}