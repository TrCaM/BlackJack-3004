package a1.blackjack.players;

import a1.blackjack.cards.Card;
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
  private PlayerMode mode;

  Player(CommandEngine commandEngine) {
    mainHand = new Hand();
    splitHand = new Hand();
    this.commandEngine = commandEngine;
    this.mode = PlayerMode.NORMAL;
    commandEngine.setup(this);
  }

  public Hand getMainHand() {
    return mainHand;
  }

  public Hand getSplitHand() {
    return splitHand;
  }

  PlayerMode getMode() {
    return mode;
  }

  public CommandEngine getCommandEngine() {
    return this.commandEngine;
  }

  /**
   * Draw a card from the deck and calculate that next state by analyzing the hand
   *  - Validate if the hit action is valid, otherwise throw
   *  - Draw a card and add to the hand based on the current mode:
   *      + If player is using split hand in split mode, then add the card to split hand
   *      + Otherwise add to the normal hand
   *  - If the hand is busted then do stand
   * @param deck
   */
  void hit(Deck deck) {
    validatePlayerStateForHit(deck);
    Card drawCard = deck.draw();
    Hand addedHand = addCardToHand(drawCard);
    if (addedHand.isBust()) {
      stand();
    }
  }

  private void setNextMode() {
  }

  private void validatePlayerStateForHit(Deck deck) {
    if (mode == PlayerMode.STANDING) {
      throw new IllegalStateException("Hit while standing");
    }
    if (deck.size() == 0) {
      throw new IllegalStateException("Hit while deck is empty");
    }
  }

  /**
   * Add card to the proper hand and return that hand
   */
  private Hand addCardToHand(Card drawCard) {
    Hand hand;
    switch (mode) {
      case SPLITING_HAND:
        hand = splitHand;
        break;
      default:
        hand = mainHand;
    }
    hand.addCard(drawCard);
    return hand;
  }

  void stand() {
    switch (mode) {
      case NORMAL:
      case SPLITING_HAND:
        mode = PlayerMode.STANDING;
        break;
      default:
        mode = PlayerMode.SPLITING_HAND;
    }
  }

  void split(Deck deck) {
    throw new UnsupportedOperationException();
  }

  /* Package private setter methods used for testing */
  void setMode(PlayerMode mode) {
    this.mode = mode;
  }

  void setMainHand(Hand hand) {
    mainHand = hand;
  }
}
