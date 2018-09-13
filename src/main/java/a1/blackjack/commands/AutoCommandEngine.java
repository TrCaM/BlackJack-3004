package a1.blackjack.commands;

import a1.blackjack.cards.Hand;
import a1.blackjack.game.Game;

/**
 * Automatically generate commands base on the current player's hand. It needs to access the state
 * of the game, specifically all the hands of the player who uses this engine to make the next
 * command:
 * - If the hand contains two identical cards with score less than or equal 16, it will split.
 * - If the hand has a score of 16 or less but can not split, or soft 17 it will hit
 * - If the hand has a score of 17 or more, it will stand.
 */
public class AutoCommandEngine implements CommandEngine {
  @Override
  public Command getNextCommand(Game game) {
    Hand activeHand = game.getActivePlayer().getPlayingHand();
    int handScore = activeHand.getHandScore();
    if (handScore < 17 && activeHand.canSplit()) {
      return Command.SPLIT;
    }
    if (handScore < 17 || activeHand.isSoft17()) {
      return Command.HIT;
    }
    return Command.STAND;
  }
}
