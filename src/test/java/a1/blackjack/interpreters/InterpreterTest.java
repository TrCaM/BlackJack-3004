package a1.blackjack.interpreters;

import a1.blackjack.cards.Card;
import a1.blackjack.cards.Suit;
import a1.blackjack.commands.Command;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
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
    assertThat(Interpreter.cardInterpret("S2"), equalTo(new Card(Suit.SPADE, 1)));
    assertThat(Interpreter.cardInterpret("D5"), equalTo(new Card(Suit.DIAMOND, 5)));
    assertThat(Interpreter.cardInterpret("H10"), equalTo(new Card(Suit.HEART, 10)));
    assertThat(Interpreter.cardInterpret("CJ"), equalTo(new Card(Suit.CLUB, 11)));
    assertThat(Interpreter.cardInterpret("SQ"), equalTo(new Card(Suit.SPADE, 12)));
    assertThat(Interpreter.cardInterpret("DK"), equalTo(new Card(Suit.DIAMOND, 13)));
    assertThat(Interpreter.cardInterpret("DA"), equalTo(new Card(Suit.DIAMOND, 1)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void cardInterpret_shouldThrow() {
    Interpreter.cardInterpret("MM");
    Interpreter.cardInterpret("H");
    Interpreter.cardInterpret("23");
  }
}