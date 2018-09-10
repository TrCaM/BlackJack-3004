package a1.blackjack.commands;

import a1.blackjack.cards.Card;
import a1.blackjack.players.Hand;
import a1.blackjack.players.Player;

import java.util.List;

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
    this.playingHand = player.getActiveHand();
    this.splitHand = player.getSplitHand();
  }
}
