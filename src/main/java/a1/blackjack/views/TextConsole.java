package a1.blackjack.views;

import a1.blackjack.commands.Command;
import a1.blackjack.interpreters.Interpreter;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.util.Scanner;
import java.util.Set;

/**
 * The view of the game using text-based interface.
 */
public class TextConsole implements Console {
  private static Logger logger = Logger.getLogger(TextConsole.class);
  private Scanner scanner;
  private boolean shouldShowInfo;

  public TextConsole() {
    this(System.in);
  }

  public TextConsole(InputStream input) {
    scanner = new Scanner(input);
    shouldShowInfo = true;
  }

  /**
   * Package-private constructor used for testing.
   */
  TextConsole(Scanner scanner) {
    this.scanner = scanner;
    shouldShowInfo = true;
  }

  @Override
  public Command promptCommand(Set<Command> availableCommands) {
    if (shouldShowInfo) {
      logger.info(getPromptMessage(availableCommands));
    }
    return Interpreter.commandInterpret(scanner.next());
  }

  private String getPromptMessage(Set<Command> availableCommands) {
    StringBuilder sb = new StringBuilder("Make your next move:\n\t");
    availableCommands.forEach(command -> sb.append(getPromptString(command)));
    return sb.toString();
  }

  private String getPromptString(Command command) {
    switch (command) {
      case STAND:
        return "- STAND --> S\n";
      case HIT:
        return "- HIT   --> H\n";
      case SPLIT:
        return "- SPLIT --> D\n";
      default:
        throw new IllegalStateException("Never happens");
    }
  }
}
