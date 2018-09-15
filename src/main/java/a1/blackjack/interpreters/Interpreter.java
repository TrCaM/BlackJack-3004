package a1.blackjack.interpreters;

import a1.blackjack.cards.Card;
import a1.blackjack.cards.Deck;
import a1.blackjack.cards.Suit;
import a1.blackjack.commands.Command;

import java.util.Queue;

/**
 * The interpreter to transform different types of input to {@link Command} or {@link Card}
 */
public class Interpreter {
  /**
   * Interpret input string to proper {@link Command}.
   * @param text the input command string
   * @return the command that is matched with the {@code text}
   */
  public static Command commandInterpret(String text) {
    switch (text.toUpperCase()) {
      case "H":
        return Command.HIT;
      case "D":
        return Command.SPLIT;
      case "S":
        return Command.STAND;
      default:
        throw new IllegalArgumentException(String.format("Unknown command: %s", text));
    }
  }

  /**
   * Interpret input string to proper {@link Card}.
   * @param text the input card string
   * @return the card that is matched with the {@code text}
   */
  public static Card cardInterpret(String text) {
    if (text.length() >= 2 && text.length() <= 3) {
      return new Card(getSuit(text), getValue(text));
    }
    throw new IllegalArgumentException(String.format("Unknown card: %s", text));
  }

  private static Suit getSuit(String text) {
    switch (Character.toUpperCase(text.charAt(0))) {
      case 'S':
        return Suit.SPADE;
      case 'D':
        return Suit.DIAMOND;
      case 'H':
        return Suit.HEART;
      case 'C':
        return Suit.CLUB;
      default:
        throw new IllegalArgumentException(String.format("Unknown card: %s", text));
    }
  }

  private static int getValue(String text) {
    switch (text.substring(1).toUpperCase()) {
      case "A":
        return 1;
      case "2":
        return 2;
      case "3":
        return 3;
      case "4":
        return 4;
      case "5":
        return 5;
      case "6":
        return 6;
      case "7":
        return 7;
      case "8":
        return 8;
      case "9":
        return 9;
      case "10":
        return 10;
      case "J":
        return 11;
      case "Q":
        return 12;
      case "K":
        return 13;
      default:
        throw new IllegalArgumentException(String.format("Unknown card: %s", text));
    }
  }

  public static void stringInterpret(String input, Queue<Command> commandQueue, Deck deck) {

  }
}
