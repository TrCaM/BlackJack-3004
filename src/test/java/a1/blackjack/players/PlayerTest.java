package a1.blackjack.players;

import a1.blackjack.cards.Card;
import a1.blackjack.cards.Deck;
import a1.blackjack.cards.Hand;
import a1.blackjack.cards.Suit;
import a1.blackjack.commands.CommandEngine;
import a1.blackjack.views.Console;
import a1.blackjack.views.TextConsole;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

public class PlayerTest {
  private static final Card C3 = new Card(Suit.CLUB, 3);
  private static final Card D7 = new Card(Suit.SPADE, 7);
  private static final Card H7 = new Card(Suit.HEART, 7);
  private static final Card CJ = new Card(Suit.CLUB, 11);
  private static final Card HQ = new Card(Suit.HEART, 12);

  private Deck deck;
  private Player player;

  @Mock
  private CommandEngine commandEngine;

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Before
  public void setUp() {
    Console console = new TextConsole();
    player = new Player(commandEngine, console, "player");
  }

  @Test
  public void getScore_normalCase_shouldSucceed() {
    Hand mainHand = new Hand(H7, D7);
    Hand splitHand = new Hand(HQ, D7);
    player.setMainHand(mainHand);
    player.setSplitHand(splitHand);

    assertThat(player.getScore(), is(17));
  }

  @Test
  public void getScore_bustedMainHand_shouldReturnSplitHandScore() {
    Hand mainHand = new Hand(HQ, CJ, C3);
    Hand splitHand = new Hand(CJ, D7);
    player.setMainHand(mainHand);
    player.setSplitHand(splitHand);

    assertThat(player.getScore(), is(17));
  }

  @Test
  public void getScore_allBusted_returnZero() {
    Hand mainHand = new Hand(HQ, CJ, C3);
    Hand splitHand = new Hand(HQ, D7, CJ);
    player.setMainHand(mainHand);
    player.setSplitHand(splitHand);

    assertThat(player.getScore(), is(0));
  }

  @Test
  public void isBusted_normalCase_returnTrue() {
    Hand mainHand = new Hand(HQ, CJ, C3);
    player.setSplitting(false);
    player.setMainHand(mainHand);

    assertThat(player.isBusted(), is(true));
  }

  @Test
  public void isBusted_splitting_returnTrue() {
    Hand mainHand = new Hand(HQ, CJ, C3);
    Hand splitHand = new Hand(HQ, D7, CJ);
    player.setSplitting(true);
    player.setMainHand(mainHand);
    player.setSplitHand(splitHand);

    assertThat(player.isBusted(), is(true));
  }

  @Test
  public void isBusted_splitting_returnFalse() {
    Hand mainHand = new Hand(HQ, CJ, C3);
    Hand splitHand = new Hand(HQ, D7);
    player.setSplitting(true);
    player.setMainHand(mainHand);
    player.setSplitHand(splitHand);

    assertThat(player.isBusted(), is(false));
  }
  @Test
  public void drawCard_normalMode_shouldSucceed() {
    List<Card> cards = Arrays.asList(D7, C3, HQ);
    deck = Deck.getDeck(cards);
    for (int i = 0; i < 2; i++) {
      player.draw(deck, false);
    }
    Hand hand = player.getMainHand();
    assertThat(hand.getCards(), contains(HQ, C3));
  }

  @Test
  public void drawCard_splitMode_shouldSucceed() {
    player.setMode(PlayerMode.SPLITTING_HAND);
    List<Card> cards = Arrays.asList(D7, C3, HQ);
    deck = Deck.getDeck(cards);
    player.draw(deck, true);
    Hand hand = player.getSplitHand();
    assertThat(hand.getCards(), contains(HQ));
  }

  @Test
  public void hit_normalMode_shouldSucceed() {
    List<Card> cards = Arrays.asList(D7, C3, HQ);
    deck = Deck.getDeck(cards);
    for (int i = 0; i < 3; i++) {
      player.hit(deck);
    }
    Hand hand = player.getMainHand();
    assertThat(hand.getCards(), contains(HQ, C3, D7));
    assertThat(player.isBusted(), is(false));
    assertThat(player.getMode(), is(PlayerMode.NORMAL));
  }

  @Test
  public void hit_standingMode_shouldSucceed() {
    List<Card> cards = Arrays.asList(D7, CJ, HQ);
    deck = Deck.getDeck(cards);
    for (int i = 0; i < 3; i++) {
      player.hit(deck);
    }
    Hand hand = player.getMainHand();
    assertThat(hand.getCards(), contains(HQ, CJ, D7));
    assertThat(player.isBusted(), is(true));
    assertThat(player.getMode(), is(PlayerMode.STANDING));
  }

  @Test
  public void hit_mainSplittingMode_shouldSucceed() {
    player.setMode(PlayerMode.SPLITTING_MAIN);
    List<Card> cards = Arrays.asList(D7, CJ, HQ);
    deck = Deck.getDeck(cards);
    for (int i = 0; i < 3; i++) {
      player.hit(deck);
    }
    Hand hand = player.getMainHand();
    assertThat(hand.getCards(), contains(HQ, CJ, D7));
    assertThat(player.isBusted(), is(true));
    assertThat(player.getMode(), is(PlayerMode.SPLITTING_HAND));
  }

  @Test(expected = IllegalStateException.class)
  public void hit_emptyDeck_shouldThrow() {
    player.hit(Deck.getEmptyDeck());
  }

  @Test(expected = IllegalStateException.class)
  public void hit_StandingMode_shouldThrow() {
    player.setMode(PlayerMode.STANDING);
    player.hit(Deck.getEmptyDeck());
  }

  @Test
  public void stand_normalMode() {
    player.stand();

    assertThat(player.getMode(), is(PlayerMode.STANDING));
  }

  @Test
  public void stand_splitMainMode() {
    player.setMode(PlayerMode.SPLITTING_MAIN);

    player.stand();

    assertThat(player.getMode(), is(PlayerMode.SPLITTING_HAND));
  }

  @Test
  public void stand_splitHandMode() {
    player.setMode(PlayerMode.SPLITTING_HAND);

    player.stand();

    assertThat(player.getMode(), is(PlayerMode.STANDING));
  }

  @Test
  public void split_normal_shouldSucceed() {
    Hand initHand = new Hand(H7, D7);
    player.setMainHand(initHand);

    player.split();

    assertThat(player.getMode(), is(PlayerMode.SPLITTING_MAIN));
    assertThat(player.getMainHand().getCards(), contains(H7));
    assertThat(player.getSplitHand().getCards(), contains(D7));
  }

  @Test(expected = IllegalStateException.class)
  public void split_StandingMode_shouldThrow() {
    player.setMode(PlayerMode.STANDING);
    player.split();
  }
}