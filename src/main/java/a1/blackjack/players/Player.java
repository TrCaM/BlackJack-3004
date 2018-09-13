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
  }

  public Hand getMainHand() {
    return mainHand;
  }

  public Hand getSplitHand() {
    return splitHand;
  }

  public Hand getPlayingHand() {
    return mode == PlayerMode.SPLITTING_HAND ? splitHand : mainHand;
  }

  PlayerMode getMode() {
    return mode;
  }

  public CommandEngine getCommandEngine() {
    return this.commandEngine;
  }

  /**
   * Draw a card from the deck and calculate that next state by analyzing the hand
   * - Validate if the hit action is valid, otherwise throw
   * - Draw a card and add to the hand based on the current mode:
   * + If player is using split hand in split mode, then add the card to split hand
   * + Otherwise add to the normal hand
   * - If the hand is busted then do stand
   */
  void hit(Deck deck) {
    validatePlayerStateForHit(deck);
    Card drawCard = deck.draw();
    Hand addedHand = addCardToHand(drawCard);
    if (addedHand.isBust()) {
      stand();
    }
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
      case SPLITTING_HAND:
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
      case SPLITTING_HAND:
        mode = PlayerMode.STANDING;
        break;
      default:
        mode = PlayerMode.SPLITTING_HAND;
    }
  }

  /**
   * Split the current hand into 2 hands given that the 2 cards of the original hand are identical
   * (this method does not check this condition). Each hand will contain 1 of the originals and draw
   * 1 more extra. If blackjack happens then the player mode should go STANDING.
   */
  void split(Deck deck) {
    validatePlayerStateForSplit(deck);
    splitHand.addCard(mainHand.removeCard(1));
    mainHand.addCard(deck.draw());
    splitHand.addCard(deck.draw());
    mode = (mainHand.isBlackjack() || splitHand.isBlackjack())
        ? PlayerMode.STANDING : PlayerMode.SPLITTING_MAIN;
  }

  private void validatePlayerStateForSplit(Deck deck) {
    if (mode == PlayerMode.STANDING) {
      throw new IllegalStateException("Split while standing");
    }
    if (deck.size() < 2) {
      throw new IllegalStateException("Split needs deck to have at least 2 cards");
    }
  }

  /* Package private setter methods used for testing */
  void setMode(PlayerMode mode) {
    this.mode = mode;
  }

  void setMainHand(Hand hand) {
    mainHand = hand;
  }
}
