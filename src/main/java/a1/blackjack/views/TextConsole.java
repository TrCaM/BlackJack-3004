package a1.blackjack.views;

import a1.blackjack.commands.Command;
import a1.blackjack.interpreters.Interpreter;

import java.io.InputStream;
import java.util.Scanner;
import java.util.Set;

/**
 * The view of the game using text-based interface.
 */
public class TextConsole implements Console {
  private Scanner scanner;
  private boolean shouldShowInfo;

  public TextConsole() {
    this(System.in);
  }

  public TextConsole(InputStream input) {
    scanner = new Scanner(input);
    shouldShowInfo = true;
  }

  @Override
  public Command promptCommand(Set<Command> availableCommands) {
    if (shouldShowInfo) {
      System.out.println(getPromptMessage(availableCommands));
    }
    return Interpreter.commandInterpret(scanner.next());
  }

  private String getPromptMessage(Set<Command> availableCommands) {
    StringBuilder sb = new StringBuilder("Make your next move:\n");
    availableCommands.forEach(command -> sb.append(getPromptString(command)));
    return sb.toString();
  }

  private String getPromptString(Command command) {
    switch (command) {
      case STAND:
        return "\t- STAND --> S\n";
      case HIT:
        return "\t- HIT   --> H\n";
      case SPLIT:
        return "\t- SPLIT --> D\n";
      default:
        throw new IllegalStateException("Never happens");
    }
  }

  @Override
  public void notify(String message) {
    System.out.println(message);
  }
}
