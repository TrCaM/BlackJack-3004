package a1.blackjack.views;

import a1.blackjack.commands.Command;

import java.util.Set;

/**
 * Interface for the view of the game (text or graphics).
 */
public interface Console {
  Command promptCommand(Set<Command> availableCommands);
}
