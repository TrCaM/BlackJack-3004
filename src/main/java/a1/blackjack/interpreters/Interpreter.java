package a1.blackjack.interpreters;

import a1.blackjack.commands.Command;

/**
 * The interpreter to transform different type of input to {@link Command}
 */
public class Interpreter {
  /**
   * Interpret input string to proper {@link Command}.
   * @param text the input command string
   * @return the command that is matched with the {@code text}
   */
  public static Command commandInterpret(String text) {
    throw new UnsupportedOperationException();
  }

  /**
   * Interpret input string to proper {@link Card}.
   * @param text the input card string
   * @return the card that is matched with the {@code text}
   */
  public static Command cardInterpret(String text) {
    throw new UnsupportedOperationException();
  }
}
