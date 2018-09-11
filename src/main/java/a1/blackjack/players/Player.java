package a1.blackjack.players;

import a1.blackjack.cards.Deck;
import a1.blackjack.cards.Hand;
import a1.blackjack.commands.CommandEngine;

/**
 * Represent a player who plays the game.
 */
public class Player {
  private Hand mainHand;
  private Hand splitHand;
  private CommandEngine commandEngine;

  public Player(CommandEngine commandEngine) {
    mainHand = new Hand();
    splitHand = new Hand();
    this.commandEngine = commandEngine;
    commandEngine.setup(this);
  }

  public Hand getActiveHand() {
    return mainHand;
  }

  public Hand getSplitHand() {
    return splitHand;
  }

  public CommandEngine getCommandEngine() {
    return this.commandEngine;
  }

  public void hit(Deck deck) {
    throw new UnsupportedOperationException();
  }

  public void stand() {
    throw new UnsupportedOperationException();
  }

  public void split(Deck deck) {
    throw new UnsupportedOperationException();
  }
}
