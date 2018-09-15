package a1.blackjack.interpreters;

import a1.blackjack.cards.Card;
import a1.blackjack.cards.Deck;
import a1.blackjack.cards.Suit;
import a1.blackjack.commands.Command;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static junit.framework.TestCase.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

public class InterpreterTest {
  @Test
  public void commandInterpret_shouldSucceed() {
    assertThat(Interpreter.commandInterpret("H"), is(Command.HIT));
    assertThat(Interpreter.commandInterpret("d"), is(Command.SPLIT));
    assertThat(Interpreter.commandInterpret("S"), is(Command.STAND));
  }

  @Test(expected = IllegalArgumentException.class)
  public void commandInterpret_shouldThrow() {
    Interpreter.cardInterpret("MM");
    Interpreter.cardInterpret("H");
    Interpreter.cardInterpret("23");
  }
  
  @Test
  public void cardInterpret_shouldSucceed() {
    assertThat(
        Interpreter.cardInterpret("S2"),
        samePropertyValuesAs(new Card(Suit.SPADE, 2)));
    assertThat(
        Interpreter.cardInterpret("D5"),
        samePropertyValuesAs(new Card(Suit.DIAMOND, 5)));
    assertThat(
        Interpreter.cardInterpret("H10"),
        samePropertyValuesAs(new Card(Suit.HEART, 10)));
    assertThat(
        Interpreter.cardInterpret("CJ"),
        samePropertyValuesAs(new Card(Suit.CLUB, 11)));
    assertThat(
        Interpreter.cardInterpret("SQ"),
        samePropertyValuesAs(new Card(Suit.SPADE, 12)));
    assertThat(
        Interpreter.cardInterpret("DK"),
        samePropertyValuesAs(new Card(Suit.DIAMOND, 13)));
    assertThat(
        Interpreter.cardInterpret("DA"),
        samePropertyValuesAs(new Card(Suit.DIAMOND, 1)));
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void cardInterpret_shouldThrow() {
    Interpreter.cardInterpret("MM");
    Interpreter.cardInterpret("H");
    Interpreter.cardInterpret("23");
  }

  @Test
  public void fileInterpreter_shouldSucceed() {
    Queue<Command> commandQueue = new LinkedList<>();
    Deck deck = Deck.getEmptyDeck();

    Interpreter.stringInterpret("H S10 S CA D2", commandQueue, deck);

    assertThat(deck.getCards().size(), is(3));

    List<Card> expectedCards = Arrays.asList(
        new Card(Suit.DIAMOND, 2),
        new Card(Suit.CLUB, 1),
        new Card(Suit.SPADE, 10));
    for (int i=0; i<3; i++) {
      assertEquals(deck.getCards().get(i), expectedCards.get(i));
    }
    assertThat(commandQueue.remove(), is(Command.HIT));
    assertThat(commandQueue.remove(), is(Command.STAND));
  }

  @Test(expected =  IllegalArgumentException.class)
  public void fileInterpreter_invalidInput_shouldThrow() {
    Queue<Command> commandQueue = new LinkedList<>();
    Deck deck = Deck.getEmptyDeck();

    Interpreter.stringInterpret("Hello", commandQueue, deck);
  }

  @Test(expected =  IllegalArgumentException.class)
  public void fileInterpreter_cardMustBeDrawnAfterHit_shouldThrow() {
    Queue<Command> commandQueue = new LinkedList<>();
    Deck deck = Deck.getEmptyDeck();

    Interpreter.stringInterpret("H S", commandQueue, deck);
  }
}