package a1.blackjack.commands;

/**
 * The source of commands to make players'moves.
 */
public interface CommandEngine {
  /**
   * Get the next {@link Command} to be executed by the player.
   * @return the command to be executed
   */
  public Command getNextCommand();
}