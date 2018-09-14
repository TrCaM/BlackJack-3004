package a1.blackjack.commands;

import a1.blackjack.cards.Hand;
import a1.blackjack.game.Game;
import a1.blackjack.views.Console;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * Produces command using the console or file input. If the player uses command read from file, all
 * file commands will be store is the command queue.
 */
public class ConsoleCommandEngine implements CommandEngine {
  private Queue<Command> fileCommandsQueue;
  private boolean isUsingFile;
  private Console console;

  public ConsoleCommandEngine(Console console) {
    this.console = console;
    this.isUsingFile = false;
  }

  public ConsoleCommandEngine(Console console, Queue<Command> fileCommandsQueue) {
    this.console = console;
    this.fileCommandsQueue = fileCommandsQueue;
    this.isUsingFile = true;
  }

  @Override
  public Command getNextCommand(Game game) {
    Set<Command> availableCommands = getAvailableCommand(game);
    Command chosenCommand;
    if (isUsingFile) {
      if (fileCommandsQueue.isEmpty()) {
        throw new IllegalArgumentException("File input doesn't provide enough commands");
      }
      chosenCommand =  fileCommandsQueue.remove();
    } else {
      chosenCommand = console.promptCommand(availableCommands);
    }
    if (!availableCommands.contains(chosenCommand)) {
      throw new IllegalArgumentException(String.format("Cannot choose command: %s", chosenCommand));
    }
    return chosenCommand;
  }

  private static Set<Command> getAvailableCommand(Game game) {
    Hand playingHand = game.getActivePlayer().getPlayingHand();
    Set<Command> commands = new HashSet<>();
    if (playingHand.getHandScore() < 21) {
      commands.add(Command.HIT);
    }
    if (playingHand.canSplit()) {
      commands.add(Command.SPLIT);
    }
    commands.add(Command.STAND);
    return commands;
  }
}
