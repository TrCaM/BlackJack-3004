package a1.blackjack.views;

import a1.blackjack.commands.Command;

import java.util.Set;

public interface Console {
  public Command promptCommand(Set<Command> availableCommands);
}
