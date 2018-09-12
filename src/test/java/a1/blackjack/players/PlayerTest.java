package a1.blackjack.players;

import a1.blackjack.cards.Card;
import a1.blackjack.cards.Deck;
import a1.blackjack.cards.Hand;
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

public class PlayerTest {
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

  private Deck deck;
  private Player player;

  @Mock
  CommandEngine commandEngine;

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Before
  public void setUp() {
    player = new Player(commandEngine);
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
    assertThat(player.getMode(), is(PlayerMode.STANDING));
  }

  @Test
  public void hit_mainSplitingMode_shouldSucceed() {
    player.setMode(PlayerMode.SPLITING_MAIN);
    List<Card> cards = Arrays.asList(D7, CJ, HQ);
    deck = Deck.getDeck(cards);
    for (int i = 0; i < 3; i++) {
      player.hit(deck);
    }
    Hand hand = player.getMainHand();
    assertThat(hand.getCards(), contains(HQ, CJ, D7));
    assertThat(player.getMode(), is(PlayerMode.SPLITING_HAND));
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
    player.setMode(PlayerMode.SPLITING_MAIN);

    player.stand();

    assertThat(player.getMode(), is(PlayerMode.SPLITING_HAND));
  }

  @Test
  public void stand_splitHandMode() {
    player.setMode(PlayerMode.SPLITING_HAND);

    player.stand();

    assertThat(player.getMode(), is(PlayerMode.STANDING));
  }

  @Test
  public void split_normal_shouldSucceed() {
    Hand initHand = new Hand(H7, D7);
    player.setMainHand(initHand);
    List<Card> cards = Arrays.asList(S8, CJ, SK, H5, DA, HQ);
    deck = Deck.getDeck(cards);

    player.split(deck);

    assertThat(player.getMode(), is(PlayerMode.SPLITING_MAIN));
    assertThat(player.getMainHand().getCards(), contains(H7, HQ));
    assertThat(player.getSplitHand().getCards(), contains(D7, DA));
  }

  @Test
  public void split_blackJackAfterSplit_mainHand_shouldStand() {
    Hand initHand = new Hand(SK, CK);
    player.setMainHand(initHand);
    List<Card> cards = Arrays.asList(S8, H5, HQ, DA);
    deck = Deck.getDeck(cards);

    player.split(deck);

    assertThat(player.getMode(), is(PlayerMode.STANDING));
    assertThat(player.getMainHand().getCards(), contains(SK, DA));
    assertThat(player.getSplitHand().getCards(), contains(CK, HQ));
  }

  @Test
  public void split_blackJackAfterSplit_splitHand_shouldStand() {
    Hand initHand = new Hand(SK, CK);
    player.setMainHand(initHand);
    List<Card> cards = Arrays.asList(CJ, H5, DA, HQ);
    deck = Deck.getDeck(cards);

    player.split(deck);

    assertThat(player.getMode(), is(PlayerMode.STANDING));
    assertThat(player.getMainHand().getCards(), contains(SK, HQ));
    assertThat(player.getSplitHand().getCards(), contains(CK, DA));
  }
}