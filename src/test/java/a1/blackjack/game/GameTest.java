package a1.blackjack.game;

import a1.blackjack.cards.Card;
import a1.blackjack.cards.Deck;
import a1.blackjack.cards.Suit;
import a1.blackjack.players.Player;
import a1.blackjack.players.PlayerMode;
import a1.blackjack.views.TextConsole;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class GameTest {
  private static final Card C3 = new Card(Suit.CLUB, 3);
  private static final Card D7 = new Card(Suit.SPADE, 7);
  private static final Card H7 = new Card(Suit.HEART, 7);
  private static final Card CJ = new Card(Suit.CLUB, 11);
  private static final Card HQ = new Card(Suit.HEART, 12);
  private static Deck deck;

  private Game game;

  @Before
  public void setUp() {
    deck = Deck.getDeck(Arrays.asList(C3, D7, H7, CJ, HQ));
    deck.getCards().forEach(Card::faceDown);
    InputStream in = new ByteArrayInputStream("S H".getBytes());
    game = Game.initConsoleInputGame(new TextConsole(in));
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
  public void playTurn_standing_doNothing() {
    Player player = game.getPlayer();

    game.playTurn(player);

    assertThat(player.getMode(), is(PlayerMode.STANDING));
  }
}