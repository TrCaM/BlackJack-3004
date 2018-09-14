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
  private PlayerMode mode;
  private String name;

  public Player(CommandEngine commandEngine, String name) {
    mainHand = new Hand();
    splitHand = new Hand();
    this.commandEngine = commandEngine;
    this.mode = PlayerMode.NORMAL;
    this.name =name;
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
   * Draw a card to the proper hand from the deck.
   */
  public void draw(Deck deck) {
    Hand hand;
    switch (mode) {
      case SPLITTING_HAND:
        hand = splitHand;
        break;
      default:
        hand = mainHand;
    }
    hand.addCard(deck.draw());
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
    draw(deck);
    if (getPlayingHand().isBust()) {
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
  void split() {
    validatePlayerStateForSplit();
    splitHand.addCard(mainHand.removeCard(1));
    mode = PlayerMode.SPLITTING_MAIN;
  }

  private void validatePlayerStateForSplit() {
    if (mode == PlayerMode.STANDING) {
      throw new IllegalStateException("Split while standing");
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
