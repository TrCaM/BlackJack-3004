package a1.blackjack.commands;

import a1.blackjack.cards.Hand;
import a1.blackjack.players.Player;

/**
 * Automatically generate commands base on the current player's hand
 */
public class AutoCommandEngine implements CommandEngine{
  private Hand playingHand;
  private Hand splitHand;

  @Override
  public Command getNextCommand() {
    return null;
  }

  @Override
  public void setup(Player player) {
    this.playingHand = player.getMainHand();
    this.splitHand = player.getSplitHand();
  }
}
